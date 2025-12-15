package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningEventResponse;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.EarningEvent;
import vn.gt.__back_end_javaspring.entity.PricingRule;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.enums.SourceType;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.exception.PricingRuleNotFound;
import vn.gt.__back_end_javaspring.exception.ReviewerNotFound;
import vn.gt.__back_end_javaspring.mapper.EarningEventMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.EarningEventService;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class EarningEventServiceImpl implements EarningEventService {
    private final ReviewerRepository reviewerRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final EarningEventRepository earningEventRepository;
    private final EarningEventMapper earningEventMapper;
    private final BlogRepository blogRepository;

    @Override
    public EarningEventResponse create(EarningEventCreateDTO earningEventCreateDTO) {

        EarningEvent earningEvent = earningEventMapper.toModel(earningEventCreateDTO);

        Reviewer reviewer = reviewerRepository.findById(earningEventCreateDTO.getReviewerId())
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

        PricingRule pricingRule = pricingRuleRepository.findById(earningEventCreateDTO.getPricingRuleId())
                .orElseThrow(() -> new PricingRuleNotFound("Pricing rule not found"));

        Blog blog = blogRepository.findById(earningEventCreateDTO.getBlogId())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        earningEvent.setReviewer(reviewer);
        earningEvent.setPricingRule(pricingRule);
        earningEvent.setBlog(blog);

        earningEventRepository.save(earningEvent);
        return earningEventMapper.toResponse(earningEvent);

    }

    @Override
    public void deleteLikeEvent(String likeId) {
        EarningEvent earningEvent = earningEventRepository.findEarningEventByLikeId(likeId);
        if (earningEvent == null) {
            throw new RuntimeException("EarningEvent not found");
        }
        earningEvent.setIsDeleted(true);
        earningEventRepository.save(earningEvent);

    }

    @Override
    public void deleteCommentEvent(String commentId) {
        EarningEvent earningEvent = earningEventRepository.findEarningEventByCommentId(commentId);
        if (earningEvent == null) {
            throw new RuntimeException("EarningEvent not found");
        }
        earningEvent.setIsDeleted(true);
        earningEventRepository.save(earningEvent);
    }

    @Override
    public void deleteShareEvent(String shareId) {
        EarningEvent earningEvent = earningEventRepository.findEarningEventByShareId(shareId);
        if (earningEvent == null) {
            throw new RuntimeException("EarningEvent not found");
        }
        earningEvent.setIsDeleted(true);
        earningEventRepository.save(earningEvent);
    }

}
