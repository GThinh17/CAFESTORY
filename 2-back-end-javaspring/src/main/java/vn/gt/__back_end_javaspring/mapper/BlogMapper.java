package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.gt.__back_end_javaspring.DTO.BlogCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogResponse;
import vn.gt.__back_end_javaspring.DTO.BlogUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Blog;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BlogMapper {

    @Mapping(
            target = "mediaUrls",
            expression =
                    "java(blog.getMediaList() == null ? null : " +
                            "blog.getMediaList().stream()" +
                            ".map(m -> m.getMediaUrl())" +
                            ".collect(java.util.stream.Collectors.toList()))"
    )

    @Mapping(source = "location.id",     target = "locationId")
    @Mapping(source = "location.name",   target = "locationName")

    @Mapping(source = "user.id",         target = "userId")
    @Mapping(source = "user.userName",   target = "userName")
    @Mapping(source = "user.fullName",   target = "userFullName")
    @Mapping(source = "user.avatar",     target = "userAvatar")

    @Mapping(source = "page.id",         target = "pageId")

    @Mapping(source = "commentsCount",   target = "commentCount")
    @Mapping(source = "likesCount",      target = "likeCount")
    @Mapping(source = "sharesCount",     target = "shareCount")
    BlogResponse toResponse(Blog blog);

    List<BlogResponse> toResponseList(List<Blog> blogs);


    Blog toModel(BlogCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Blog entity, BlogUpdateDTO dto);
}
