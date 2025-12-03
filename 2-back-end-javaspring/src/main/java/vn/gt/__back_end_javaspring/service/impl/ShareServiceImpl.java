package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ShareCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ShareReponse;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.SourceType;
import vn.gt.__back_end_javaspring.exception.*;
import vn.gt.__back_end_javaspring.mapper.ShareMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.EarningEventService;
import vn.gt.__back_end_javaspring.service.ReviewerService;
import vn.gt.__back_end_javaspring.service.ShareService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final ShareMapper shareMapper;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final ReviewerService reviewerService;
    private final ReviewerRepository reviewerRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final EarningEventService earningEventService;

    @Override
    public ShareReponse createShare(ShareCreateDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Blog blog = blogRepository.findById(dto.getBlogId())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

        blog.setSharesCount(blog.getSharesCount() + 1);
        blogRepository.save(blog);

        Share share = shareMapper.toEntity(dto);
        Share saved = shareRepository.save(share);

        String reviewerId = blog.getUser().getId();
        if(reviewerService.isReviewer(reviewerId)){
            Reviewer reviewer = reviewerRepository.findById(reviewerId)
                    .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

            PricingRule pricingRule = pricingRuleRepository.findFirstByIsActiveTrue();
            if (pricingRule == null) {
                throw new PricingRuleNotFound("No active pricing rule");
            }

            BigDecimal weight = pricingRule.getShareWeight();
            if (weight == null) {
                weight = BigDecimal.ZERO;
            }
            BigDecimal unitPrice = pricingRule.getUnitPrice();
            BigDecimal amount = unitPrice.multiply(weight);

            EarningEventCreateDTO earningEventCreateDTO = new EarningEventCreateDTO();
            earningEventCreateDTO.setBlogId(dto.getBlogId());
            earningEventCreateDTO.setSourceType("SHARE");
            earningEventCreateDTO.setPricingRuleId(pricingRule.getId());
            earningEventCreateDTO.setReviewerId(reviewerId);
            earningEventCreateDTO.setAmount(amount);

            earningEventService.create(earningEventCreateDTO);

        }

        return shareMapper.toResponse(saved);
    }

    @Override
    public ShareReponse getShareById(String shareId) {
        Share share = shareRepository.findByIdAndIsDeletedFalse(shareId)
                .orElseThrow(() -> new ShareNotFoundException("Share not found"));
        return shareMapper.toResponse(share);
    }

    @Override
    public List<ShareReponse> getSharesByBlog(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        List<Share> shares = shareRepository.findByBlog_IdAndIsDeletedFalseOrderByCreatedAtDesc(blogId);
        return shareMapper.toResponseList(shares);
    }

    @Override
    public List<ShareReponse> getSharesByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Share> shares = shareRepository.findByUser_IdAndIsDeletedFalseOrderByCreatedAtDesc(userId);
        return shareMapper.toResponseList(shares);
    }

    @Override
    public void softDeleteShare(String shareId, String userId) {
        Share share = shareRepository.findByIdAndIsDeletedFalse(shareId)
                .orElseThrow(() -> new ShareNotFoundException("Share not found"));
        if (!share.getUser().getId().equals(userId)) {
            throw new UserNotFoundException("You are not allowed to delete this share");
        }
        share.setIsDeleted(true);
        shareRepository.save(share);
    }
}
