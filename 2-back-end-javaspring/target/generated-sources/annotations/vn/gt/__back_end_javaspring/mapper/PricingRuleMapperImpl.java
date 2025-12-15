package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.PricingRuleCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PricingRuleResponse;
import vn.gt.__back_end_javaspring.entity.PricingRule;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T03:24:30+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class PricingRuleMapperImpl implements PricingRuleMapper {

    @Override
    public PricingRule toEntity(PricingRuleCreateDTO pricingRuleCreateDTO) {
        if ( pricingRuleCreateDTO == null ) {
            return null;
        }

        PricingRule pricingRule = new PricingRule();

        pricingRule.setName( pricingRuleCreateDTO.getName() );
        pricingRule.setDescription( pricingRuleCreateDTO.getDescription() );
        pricingRule.setLikeWeight( pricingRuleCreateDTO.getLikeWeight() );
        pricingRule.setCommentWeight( pricingRuleCreateDTO.getCommentWeight() );
        pricingRule.setShareWeight( pricingRuleCreateDTO.getShareWeight() );
        pricingRule.setUnitPrice( pricingRuleCreateDTO.getUnitPrice() );
        pricingRule.setEffectiveFrom( pricingRuleCreateDTO.getEffectiveFrom() );
        pricingRule.setEffectiveTo( pricingRuleCreateDTO.getEffectiveTo() );

        return pricingRule;
    }

    @Override
    public PricingRuleResponse toResponse(PricingRule pricingRule) {
        if ( pricingRule == null ) {
            return null;
        }

        PricingRuleResponse.PricingRuleResponseBuilder pricingRuleResponse = PricingRuleResponse.builder();

        pricingRuleResponse.pricingRuleResponseId( pricingRule.getId() );
        pricingRuleResponse.name( pricingRule.getName() );
        pricingRuleResponse.description( pricingRule.getDescription() );
        pricingRuleResponse.likeWeight( pricingRule.getLikeWeight() );
        pricingRuleResponse.commentWeight( pricingRule.getCommentWeight() );
        pricingRuleResponse.shareWeight( pricingRule.getShareWeight() );
        pricingRuleResponse.unitPrice( pricingRule.getUnitPrice() );
        pricingRuleResponse.currency( pricingRule.getCurrency() );
        pricingRuleResponse.effectiveFrom( pricingRule.getEffectiveFrom() );
        pricingRuleResponse.effectiveTo( pricingRule.getEffectiveTo() );
        pricingRuleResponse.isActive( pricingRule.getIsActive() );
        pricingRuleResponse.createdAt( pricingRule.getCreatedAt() );

        return pricingRuleResponse.build();
    }

    @Override
    public List<PricingRuleResponse> toResponseList(List<PricingRule> pricingRules) {
        if ( pricingRules == null ) {
            return null;
        }

        List<PricingRuleResponse> list = new ArrayList<PricingRuleResponse>( pricingRules.size() );
        for ( PricingRule pricingRule : pricingRules ) {
            list.add( toResponse( pricingRule ) );
        }

        return list;
    }
}
