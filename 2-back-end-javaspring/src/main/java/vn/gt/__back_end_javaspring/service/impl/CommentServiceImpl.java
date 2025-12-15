package vn.gt.__back_end_javaspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.gt.__back_end_javaspring.DTO.*;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.NotificationType;
import vn.gt.__back_end_javaspring.exception.*;
import vn.gt.__back_end_javaspring.mapper.CommentMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.CommentService;
import vn.gt.__back_end_javaspring.service.EarningEventService;
import vn.gt.__back_end_javaspring.service.NotificationService;
import vn.gt.__back_end_javaspring.service.ReviewerService;
import vn.gt.__back_end_javaspring.util.CursorUtil;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final commentImageRepository commentImageRepository;
    private final ReviewerService reviewerService;
    private final ReviewerRepository reviewerRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final EarningEventService earningEventService;
    private final NotificationService notificationService;

    @Override
    @Transactional(readOnly = true)
    public CursorPage<CommentResponse> getCommentsNewestByBlogId(String blogId, String cursor, int size) {
        List<Comment> comments;
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new BlogNotFoundException("Blog not found"));

        Pageable pageRequest = PageRequest.of(0, size);
        if (cursor == null || cursor.isBlank()) {
            comments = commentRepository.firstPageCommentBlog(blog.getId(), pageRequest);
        } else {
            var p = CursorUtil.decode(cursor);
            var lastCreatedAt = p.getLeft();
            var lastId = p.getRight();
            comments = commentRepository.nextPageCommentBlog(lastCreatedAt, lastId, blogId, pageRequest);
        }
        var items = commentMapper.toResponseList(comments);
        String nextCursor = null;
        if (comments.size() == size) {
            var last = comments.get(comments.size() - 1);
            nextCursor = CursorUtil.encode(last.getCreatedAt(), last.getId());
        }

        return CursorPage.<CommentResponse>builder().data(items).nextCursor(nextCursor).build();
    }

    @Override
    public CommentResponse getCommentById(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        return commentMapper.toResponse(comment);

    }

    @Override
    public CommentResponse addComment(CommentCreateDTO dto) {
        Blog blog = blogRepository.findById(dto.getBlogId())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comment parent = null;
        if (dto.getCommentParentId() != null && !dto.getCommentParentId().isBlank()) {
            parent = commentRepository.findById(dto.getCommentParentId())
                    .orElseThrow(() -> new CommentNotFoundException("Parent comment not found"));

        }

        CommentImage commentImage = null;
        if (dto.getCommentImageUrl() != null && !dto.getCommentImageUrl().isBlank()) {
            commentImage = commentImageRepository.findById(dto.getCommentImageUrl())
                    .orElseThrow(() -> new CommentNotFoundException("Comment image not found"));
        }

        blog.setCommentsCount(blog.getCommentsCount() + 1);

        if (parent != null) {
            if (parent.getReplyCount() == null)
                parent.setReplyCount(0L);
            parent.setReplyCount(parent.getReplyCount() + 1);
        }

        Comment comment = new Comment();
        comment.setBlog(blog);
        comment.setUser(user);
        comment.setParentComment(parent);
        comment.setContent(dto.getContent());
        comment.setCommentImage(commentImage);

        Comment saved = commentRepository.save(comment);
        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
        String senderId = user.getId();

        //Notification
        if(parent == null) {
            String receiverId = blog.getUser().getId();
            notificationRequestDTO.setSenderId(senderId);
            notificationRequestDTO.setReceiverId(receiverId);
            notificationRequestDTO.setType(NotificationType.COMMENT_POST);
            notificationRequestDTO.setPostId(blog.getId());
            notificationRequestDTO.setCommentId(null);
            notificationRequestDTO.setPageId(null);
            notificationRequestDTO.setWalletTransactionId(null);
            notificationRequestDTO.setBadgeId(null);
            notificationRequestDTO.setBody(user.getFullName() + " đã bình luận bài viết của bạn");

            notificationService.sendNotification(receiverId, notificationRequestDTO);
        } else{
            String receiverId = parent.getUser().getId();
            notificationRequestDTO.setSenderId(senderId);
            notificationRequestDTO.setReceiverId(receiverId);
            notificationRequestDTO.setType(NotificationType.REPLY_COMMENT);
            notificationRequestDTO.setPostId(null);
            notificationRequestDTO.setCommentId(parent.getId());
            notificationRequestDTO.setPageId(null);
            notificationRequestDTO.setWalletTransactionId(null);
            notificationRequestDTO.setBadgeId(null);
            notificationRequestDTO.setBody(user.getFullName() + " đã trả lời bình luận của bạn");

            notificationService.sendNotification(receiverId, notificationRequestDTO);

        }
        // Giữ logic earning event như cũ
        String userId = blog.getUser().getId();

        if (reviewerService.isReviewerByUserId(userId)) {
            Reviewer reviewer = reviewerRepository.findByUser_Id(userId);
            if(reviewer==null) {
                throw new ReviewerNotFound("Reviewer not found");
            }

            PricingRule pricingRule = pricingRuleRepository.findFirstByIsActiveTrue();
            if (pricingRule == null)
                throw new PricingRuleNotFound("No active pricing rule");

            BigDecimal weight = pricingRule.getCommentWeight();
            if (weight == null)
                weight = BigDecimal.ZERO;

            BigDecimal amount = pricingRule.getUnitPrice().multiply(weight);

            EarningEventCreateDTO earningEventCreateDTO = new EarningEventCreateDTO();
            earningEventCreateDTO.setBlogId(dto.getBlogId());
            earningEventCreateDTO.setSourceType("COMMENT");
            earningEventCreateDTO.setPricingRuleId(pricingRule.getId());
            earningEventCreateDTO.setReviewerId(reviewer.getId());
            earningEventCreateDTO.setAmount(amount);
            earningEventCreateDTO.setCommentId(saved.getId());

            earningEventService.create(earningEventCreateDTO);
        }
        return commentMapper.toResponse(saved);
    }

    @Override
    public CommentResponse updateComment(String commentId, CommentUpdateDTO commentUpdateDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        commentMapper.updateEntity(comment, commentUpdateDTO);
        comment.setIsEdited(true);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toResponse(saved);
    }

    // @Override //HardDelete
    // public CommentResponse deleteComment(String commentId) {
    // Comment comment = commentRepository.findById(commentId)
    // .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    // commentRepository.delete(comment);
    // return commentMapper.toResponse(comment);
    // }

    // Soft Delete
    @Override
    public CommentResponse deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        Blog blog = blogRepository.findById(comment.getBlog().getId())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        blog.setCommentsCount(blog.getCommentsCount() - 1);
        comment.setIsDeleted(true);
        commentRepository.save(comment);

        //Xoa earningEvent
        earningEventService.deleteCommentEvent(commentId);

        return commentMapper.toResponse(commentRepository.save(comment));
    }
}
