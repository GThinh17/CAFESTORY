package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.gt.__back_end_javaspring.DTO.PricingRuleCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PricingRuleResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.DTO.TagDTO;
import vn.gt.__back_end_javaspring.DTO.TagResponse;
import vn.gt.__back_end_javaspring.entity.PricingRule;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.Tag;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    Tag toEntity(TagDTO dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.fullName")
    @Mapping(target = "blogTagId", source = "blogTag.id")
    @Mapping(target = "userTagId", source = "userTag.id")
    @Mapping(target = "pageTagId", source = "pageTag.id")
    TagResponse toResponse(Tag tag);

    List<TagResponse> toResponseList(List<Tag> tags);
}
