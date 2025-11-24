package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.BlogUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentResponse;
import vn.gt.__back_end_javaspring.DTO.CommentUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "blogId", source = "blog.id")
    @Mapping(target = "parentCommentId", source = "parentComment.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "userAvatar", source = "user.avatar")
    @Mapping(target = "commentImageId", source = "commentImage.id")
    @Mapping(target = "commentImageUrl", source = "commentImage.imageUrl")
    CommentResponse toResponse(Comment comment);

    List<CommentResponse>  toResponseList(List<Comment> comments);

    @Mapping(source = "blogId", target = "blog.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "commentImageId", target = "commentImage.id")
    Comment toModel(CommentCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Comment entity, CommentUpdateDTO dto);

}
