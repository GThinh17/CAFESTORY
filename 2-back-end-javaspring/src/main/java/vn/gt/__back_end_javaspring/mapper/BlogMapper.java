package vn.gt.__back_end_javaspring.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.DTO.BlogRequest;
import vn.gt.__back_end_javaspring.entity.DTO.BlogResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "user.fullName", target = "userFullName")
    @Mapping(source = "user.avatar", target = "userAvatar")
    BlogResponse toResponse(Blog blog);


    //@Mapping(target = "user.id", source = "userId")
    Blog toModel (BlogRequest blogRequest);

    List<BlogResponse> toResponseList(List<Blog> blogs);
}
