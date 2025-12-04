package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.gt.__back_end_javaspring.DTO.PricingRuleCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PricingRuleResponse;
import vn.gt.__back_end_javaspring.entity.PricingRule;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PricingRuleMapper {
    PricingRule toEntity(PricingRuleCreateDTO pricingRuleCreateDTO);

    @Mapping(source = "id", target = "pricingRuleResponseId")
    PricingRuleResponse toResponse(PricingRule pricingRule);

    List<PricingRuleResponse> toResponseList(List<PricingRule> pricingRules);
}
