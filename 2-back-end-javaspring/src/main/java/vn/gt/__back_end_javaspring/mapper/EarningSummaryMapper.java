package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.EarningSummaryCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningSummaryResponse;
import vn.gt.__back_end_javaspring.entity.EarningSummary;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EarningSummaryMapper {
    EarningSummary toModel(EarningSummaryCreateDTO dto);

    @Mapping(source = "reviewer.id", target = "reviewerId")
    @Mapping(source = "reviewer.user.id", target = "userId")
    @Mapping(source = "reviewer.user.fullName", target = "userName")
    @Mapping(source = "followRule.id", target = "followRuleId")
    @Mapping(source = "followRule.minFollow", target = "followRuleMin")
    @Mapping(source = "followRule.maxFollow", target = "followRuleMax")
    EarningSummaryResponse toResponse(EarningSummary entity);

    List<EarningSummaryResponse> toResponseList(List<EarningSummary> entity);

    // @BeanMapping(nullValuePropertyMappingStrategy =
    // NullValuePropertyMappingStrategy.IGNORE)
    // void toUpdateEntity(@MappingTarget EarningSummary entity, );
}
