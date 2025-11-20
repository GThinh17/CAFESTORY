package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.BlogLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogLikeResponse;
import vn.gt.__back_end_javaspring.entity.BlogLike;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogLikeMapper {

    BlogLike toModel(BlogLikeCreateDTO blogLikeCreateDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "blogId", source = "blog.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "userAvatar", source = "user.avatar")
    BlogLikeResponse toResponse(BlogLike bloglikeid);

    List<BlogLikeResponse> toResponseList(List<BlogLike> bloglikeids);
}


