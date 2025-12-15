package vn.gt.__back_end_javaspring.mapper;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningEventResponse;
import vn.gt.__back_end_javaspring.entity.EarningEvent;
import vn.gt.__back_end_javaspring.entity.PricingRule;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.enums.SourceType;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T03:24:30+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class EarningEventMapperImpl implements EarningEventMapper {

    @Override
    public EarningEvent toModel(EarningEventCreateDTO earningEventCreateDTO) {
        if ( earningEventCreateDTO == null ) {
            return null;
        }

        EarningEvent.EarningEventBuilder earningEvent = EarningEvent.builder();

        earningEvent.amount( earningEventCreateDTO.getAmount() );
        if ( earningEventCreateDTO.getSourceType() != null ) {
            earningEvent.sourceType( Enum.valueOf( SourceType.class, earningEventCreateDTO.getSourceType() ) );
        }

        return earningEvent.build();
    }

    @Override
    public EarningEventResponse toResponse(EarningEvent earningEvent) {
        if ( earningEvent == null ) {
            return null;
        }

        EarningEventResponse.EarningEventResponseBuilder earningEventResponse = EarningEventResponse.builder();

        earningEventResponse.earningEventId( earningEvent.getId() );
        earningEventResponse.reviewerId( earningEventReviewerId( earningEvent ) );
        earningEventResponse.likeWeight( earningEventPricingRuleLikeWeight( earningEvent ) );
        earningEventResponse.commentWeight( earningEventPricingRuleCommentWeight( earningEvent ) );
        earningEventResponse.shareWeight( earningEventPricingRuleShareWeight( earningEvent ) );
        earningEventResponse.unitPrice( earningEventPricingRuleUnitPrice( earningEvent ) );
        earningEventResponse.amount( earningEvent.getAmount() );
        if ( earningEvent.getSourceType() != null ) {
            earningEventResponse.sourceType( earningEvent.getSourceType().name() );
        }
        earningEventResponse.createdAt( earningEvent.getCreatedAt() );

        return earningEventResponse.build();
    }

    private String earningEventReviewerId(EarningEvent earningEvent) {
        Reviewer reviewer = earningEvent.getReviewer();
        if ( reviewer == null ) {
            return null;
        }
        return reviewer.getId();
    }

    private BigDecimal earningEventPricingRuleLikeWeight(EarningEvent earningEvent) {
        PricingRule pricingRule = earningEvent.getPricingRule();
        if ( pricingRule == null ) {
            return null;
        }
        return pricingRule.getLikeWeight();
    }

    private BigDecimal earningEventPricingRuleCommentWeight(EarningEvent earningEvent) {
        PricingRule pricingRule = earningEvent.getPricingRule();
        if ( pricingRule == null ) {
            return null;
        }
        return pricingRule.getCommentWeight();
    }

    private BigDecimal earningEventPricingRuleShareWeight(EarningEvent earningEvent) {
        PricingRule pricingRule = earningEvent.getPricingRule();
        if ( pricingRule == null ) {
            return null;
        }
        return pricingRule.getShareWeight();
    }

    private BigDecimal earningEventPricingRuleUnitPrice(EarningEvent earningEvent) {
        PricingRule pricingRule = earningEvent.getPricingRule();
        if ( pricingRule == null ) {
            return null;
        }
        return pricingRule.getUnitPrice();
    }
}
