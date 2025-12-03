package vn.gt.__back_end_javaspring.repository;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.gt.__back_end_javaspring.DTO.EarningEventCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningEventResponse;
import vn.gt.__back_end_javaspring.entity.EarningEvent;

@Mapper(componentModel = "spring")
public interface EarningEventMapper {
    EarningEvent toModel(EarningEventCreateDTO earningEventCreateDTO);

    @Mapping(source = "id", target = "earningEventId")
    @Mapping(source = "reviewer.id", target = "reviewerId")
    @Mapping(source = "pricingRule.likeWeight", target = "likeWeight")
    @Mapping(source = "pricingRule.commentWeight", target = "commentWeight")
    @Mapping(source = "pricingRule.shareWeight", target = "shareWeight")
    @Mapping(source = "pricingRule.unitPrice", target = "unitPrice")
    EarningEventResponse toResponse(EarningEvent earningEvent);
}
