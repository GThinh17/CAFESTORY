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
import vn.gt.__back_end_javaspring.service.NotificationClient;
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
    private final NotificationClient notificationClient;

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

        String useId = blog.getUser().getId();
        if(reviewerService.isReviewerByUserId(userId)){
            Reviewer reviewer = reviewerRepository.findByUser_Id(userId);
            System.out.println("Reviewer:  " + reviewer);

            PricingRule pricingRule = pricingRuleRepository.findFirstByIsActiveTrue();

            EarningEventCreateDTO earningEventCreateDTO = new EarningEventCreateDTO();

            BigDecimal weight = pricingRule.getLikeWeight();
            BigDecimal unitPrice = pricingRule.getUnitPrice();

            earningEventCreateDTO.setBlogId(blogId);
            earningEventCreateDTO.setSourceType("LIKE");
            earningEventCreateDTO.setPricingRuleId(pricingRule.getId());
            earningEventCreateDTO.setReviewerId(reviewer.getId());
            earningEventCreateDTO.setAmount(weight.multiply(unitPrice));
            System.out.println("earningEventCreateDTO:  " + earningEventCreateDTO);
            earningEventService.create(earningEventCreateDTO);
        }

        blog.setLikesCount(blog.getLikesCount() + 1);
        blogRepository.save(blog);

        BlogLike bloglike = blogLikeMapper.toModel(request);
        BlogLike saved = blogLikeRepository.save(bloglike);

        //Notification
        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
        notificationRequestDTO.setSenderId(userId);
        notificationRequestDTO.setReceiverId(blog.getUser().getId());
        notificationRequestDTO.setType(NotificationType.LIKE_POST);
        notificationRequestDTO.setPostId(blogId);
        notificationRequestDTO.setCommentId(null);
        notificationRequestDTO.setPageId(null);
        notificationRequestDTO.setWalletTransactionId(null);
        notificationRequestDTO.setBadgeId(null);
        notificationRequestDTO.setContent(user.getFullName() + "đã thích bài viết của bạn");

        notificationClient.sendNotification(notificationRequestDTO);

        return blogLikeMapper.toResponse(saved);

    }

    @Override
    public void unlike(String blogId, String userId) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        blog.setLikesCount(blog.getLikesCount() - 1);

        System.out.println("blogId: " + blogId + ", userId: " + userId);

        if (!blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeNotFoundException("Like not exists");
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
