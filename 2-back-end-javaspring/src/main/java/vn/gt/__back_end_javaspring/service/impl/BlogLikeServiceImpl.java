package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.BlogLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogLikeResponse;
import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.exception.*;
import vn.gt.__back_end_javaspring.mapper.BlogLikeMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.BlogLikeService;
import vn.gt.__back_end_javaspring.service.EarningEventService;
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
    private final ReviewerService  reviewerService;
    private final ReviewerRepository reviewerRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final EarningEventService earningEventService;

    @Override
    public BlogLikeResponse like(BlogLikeCreateDTO request) {
        String userId = request.getUserId();
        String blogId = request.getBlogId();

        //Tran like doube
        if(blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeExist("Like already exists");
        }



        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));


        String useId = blog.getUser().getId();
        System.out.println("ReviewerId: " + userId);
        System.out.println("Boolean: "+ reviewerService.isReviewerByUserId(userId));
        if(reviewerService.isReviewerByUserId(userId)){
            Reviewer reviewer = reviewerRepository.findById(userId)
                    .orElseThrow(()-> new ReviewerNotFound("Reviewer not found"));

            PricingRule pricingRule = pricingRuleRepository.findFirstByIsActiveTrue();
            System.out.println("pricingRule = " + pricingRule.toString());

            EarningEventCreateDTO earningEventCreateDTO = new EarningEventCreateDTO();

            BigDecimal weight = pricingRule.getLikeWeight();
            BigDecimal unitPrice = pricingRule.getUnitPrice();

            earningEventCreateDTO.setBlogId(blogId);
            earningEventCreateDTO.setSourceType("LIKE");
            earningEventCreateDTO.setPricingRuleId(pricingRule.getId());
            earningEventCreateDTO.setReviewerId(userId);
            earningEventCreateDTO.setAmount(weight.multiply(unitPrice));
            earningEventService.create(earningEventCreateDTO);
        }

        blog.setLikesCount(blog.getLikesCount() + 1);
        blogRepository.save(blog);

        BlogLike bloglike = blogLikeMapper.toModel(request);
        BlogLike saved =  blogLikeRepository.save(bloglike);

        return blogLikeMapper.toResponse(saved);

    }

    @Override
    public void unlike(String blogId, String userId) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        blog.setLikesCount(blog.getLikesCount() - 1);

        System.out.println("blogId: " + blogId + ", userId: " + userId);

        if(!blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
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
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        return blogLikeRepository.countByBlog_Id(blogId);
    }

    @Override
    public List<BlogLikeResponse> getLikesByBlog(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        return blogLikeMapper.toResponseList(blogLikeRepository.findByBlog_Id(blogId));
    }
}
