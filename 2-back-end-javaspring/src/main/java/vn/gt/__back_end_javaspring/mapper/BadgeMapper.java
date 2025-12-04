package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.BadgeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BadgeResponse;
import vn.gt.__back_end_javaspring.DTO.BadgeUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Badge;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BadgeMapper {
    Badge toEntity(BadgeCreateDTO dto);

    @Mapping(source = "id", target = "badgeId")
    BadgeResponse toResponse(Badge entity);

    List<BadgeResponse> toResponses(List<Badge> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(BadgeUpdateDTO dto, @MappingTarget Badge entity);
}
