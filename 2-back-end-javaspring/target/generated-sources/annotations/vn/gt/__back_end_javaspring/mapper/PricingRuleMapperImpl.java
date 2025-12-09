package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.PricingRuleCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PricingRuleResponse;
import vn.gt.__back_end_javaspring.entity.PricingRule;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2025-12-09T16:04:00+0700", comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)")
@Component
public class PricingRuleMapperImpl implements PricingRuleMapper {

    @Override
    public PricingRule toEntity(PricingRuleCreateDTO pricingRuleCreateDTO) {
        if (pricingRuleCreateDTO == null) {
            return null;
        }

        PricingRule pricingRule = new PricingRule();

        pricingRule.setCommentWeight(pricingRuleCreateDTO.getCommentWeight());
        pricingRule.setDescription(pricingRuleCreateDTO.getDescription());
        pricingRule.setEffectiveFrom(pricingRuleCreateDTO.getEffectiveFrom());
        pricingRule.setEffectiveTo(pricingRuleCreateDTO.getEffectiveTo());
        pricingRule.setLikeWeight(pricingRuleCreateDTO.getLikeWeight());
        pricingRule.setName(pricingRuleCreateDTO.getName());
        pricingRule.setShareWeight(pricingRuleCreateDTO.getShareWeight());
        pricingRule.setUnitPrice(pricingRuleCreateDTO.getUnitPrice());

        return pricingRule;
    }

    @Override
    public PricingRuleResponse toResponse(PricingRule pricingRule) {
        if (pricingRule == null) {
            return null;
        }

        PricingRuleResponse.PricingRuleResponseBuilder pricingRuleResponse = PricingRuleResponse.builder();

        pricingRuleResponse.pricingRuleResponseId(pricingRule.getId());
        pricingRuleResponse.commentWeight(pricingRule.getCommentWeight());
        pricingRuleResponse.createdAt(pricingRule.getCreatedAt());
        pricingRuleResponse.currency(pricingRule.getCurrency());
        pricingRuleResponse.description(pricingRule.getDescription());
        pricingRuleResponse.effectiveFrom(pricingRule.getEffectiveFrom());
        pricingRuleResponse.effectiveTo(pricingRule.getEffectiveTo());
        pricingRuleResponse.isActive(pricingRule.getIsActive());
        pricingRuleResponse.likeWeight(pricingRule.getLikeWeight());
        pricingRuleResponse.name(pricingRule.getName());
        pricingRuleResponse.shareWeight(pricingRule.getShareWeight());
        pricingRuleResponse.unitPrice(pricingRule.getUnitPrice());

        return pricingRuleResponse.build();
    }

    @Override
    public List<PricingRuleResponse> toResponseList(List<PricingRule> pricingRules) {
        if (pricingRules == null) {
            return null;
        }

        List<PricingRuleResponse> list = new ArrayList<PricingRuleResponse>(pricingRules.size());
        for (PricingRule pricingRule : pricingRules) {
            list.add(toResponse(pricingRule));
        }

        return list;
    }
}
