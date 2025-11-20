package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.LikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.LikeResponse;
import vn.gt.__back_end_javaspring.DTO.LikeUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Like;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    Like toModel(LikeCreateDTO likeCreateDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "blogId", source = "blog.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "userAvatar", source = "user.avatar")
    LikeResponse toResponse(Like like);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Like entity, LikeUpdateDTO dto);

    List<LikeResponse> toResponseList(List<Like> likes);
}


