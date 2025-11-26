package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.CommentLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentLikeResponse;
import vn.gt.__back_end_javaspring.entity.CommentLike;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentLikeMapper {

    CommentLike toModel(CommentLikeCreateDTO commentLikeCreateDTO);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "commentId", source = "comment.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "userAvatar", source = "user.avatar")
    CommentLikeResponse toResponse(CommentLike commentLike);

    List<CommentLikeResponse> toCommentResponseList(List<CommentLike> commentLikes);

}
