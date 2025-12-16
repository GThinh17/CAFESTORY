package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.BlogLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogLikeResponse;
import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.NotificationType;
import vn.gt.__back_end_javaspring.exception.*;
import vn.gt.__back_end_javaspring.mapper.BlogLikeMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.BlogLikeService;
import vn.gt.__back_end_javaspring.service.EarningEventService;
import vn.gt.__back_end_javaspring.service.NotificationService;
import vn.gt.__back_end_javaspring.service.ReviewerService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogLikeServiceImpl implements BlogLikeService {
    private final BlogLikeRepository blogLikeRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final BlogLikeMapper blogLikeMapper;
    private final ReviewerService reviewerService;
    private final ReviewerRepository reviewerRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final EarningEventService earningEventService;
    private final NotificationService notificationService;

    @Override
    public BlogLikeResponse like(BlogLikeCreateDTO request) {
        String userId = request.getUserId();
        String blogId = request.getBlogId();

        // Tran like doube
        if (blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeExist("Like already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        blog.setLikesCount(blog.getLikesCount() + 1);
        blogRepository.save(blog);

        BlogLike bloglike = blogLikeMapper.toModel(request);
        BlogLike saved = blogLikeRepository.save(bloglike);

        // Notification
        String senderId = user.getId();
        String receiverId = blog.getUser().getId();
        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
        notificationRequestDTO.setSenderId(senderId);
        notificationRequestDTO.setReceiverId(receiverId);
        notificationRequestDTO.setType(NotificationType.LIKE_POST);
        notificationRequestDTO.setPostId(blogId);
        notificationRequestDTO.setCommentId(null);
        notificationRequestDTO.setPageId(null);
        notificationRequestDTO.setWalletTransactionId(null);
        notificationRequestDTO.setBadgeId(null);
        notificationRequestDTO.setBody(user.getFullName() + " đã thích bài viết của bạn");

        // Set EarningEvent
        String userReviewerId = blog.getUser().getId();
        if (reviewerService.isReviewerByUserId(userReviewerId)) {
            Reviewer reviewer = reviewerRepository.findByUser_Id(userReviewerId);
            if (reviewer == null) {
                throw new ReviewerNotFound("Reviewer not found");
            }

            PricingRule pricingRule = pricingRuleRepository.findFirstByIsActiveTrue();

            EarningEventCreateDTO earningEventCreateDTO = new EarningEventCreateDTO();

            BigDecimal weight = pricingRule.getLikeWeight();
            BigDecimal unitPrice = pricingRule.getUnitPrice();

            earningEventCreateDTO.setBlogId(blogId);
            earningEventCreateDTO.setSourceType("LIKE");
            earningEventCreateDTO.setPricingRuleId(pricingRule.getId());
            earningEventCreateDTO.setReviewerId(reviewer.getId());
            earningEventCreateDTO.setAmount(weight.multiply(unitPrice));
            System.out.println("saved : " + saved.getId());
            earningEventCreateDTO.setLikeId(saved.getId());
            System.out.println("after saved : " + earningEventCreateDTO.getLikeId());

            earningEventService.create(earningEventCreateDTO);
        }

        notificationService.sendNotification(receiverId, notificationRequestDTO);

        return blogLikeMapper.toResponse(saved);

    }

    @Override
    public void unlike(String blogId, String userId) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        blog.setLikesCount(blog.getLikesCount() - 1);

        if (!blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeNotFoundException("Like not exists");
        }

        System.out.println("blogId: " + blogId + ", userId: " + userId);

        // Xoa EarningEvent
        BlogLike blogLike = blogLikeRepository.findByUser_IdAndBlog_Id(userId, blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        if(reviewerService.isReviewerByUserId(userId)){
            earningEventService.deleteLikeEvent(blogLike.getId());

        }
        
        blogLikeRepository.deleteByUserAndBlog(userId, blogId);

    }

    @Override
    public boolean isLiked(String userId, String blogId) {
        return blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId);
    }

    @Override
    public long countLikes(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        return blogLikeRepository.countByBlog_Id(blogId);
    }

    @Override
    public List<BlogLikeResponse> getLikesByBlog(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        return blogLikeMapper.toResponseList(blogLikeRepository.findByBlog_Id(blogId));
    }
}
