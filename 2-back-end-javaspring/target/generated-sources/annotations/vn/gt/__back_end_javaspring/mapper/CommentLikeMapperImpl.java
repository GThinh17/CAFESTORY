package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.CommentLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentLikeResponse;
import vn.gt.__back_end_javaspring.entity.Comment;
import vn.gt.__back_end_javaspring.entity.CommentLike;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T16:55:23+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class CommentLikeMapperImpl implements CommentLikeMapper {

    @Override
    public CommentLike toModel(CommentLikeCreateDTO commentLikeCreateDTO) {
        if ( commentLikeCreateDTO == null ) {
            return null;
        }

        CommentLike commentLike = new CommentLike();

        return commentLike;
    }

    @Override
    public CommentLikeResponse toResponse(CommentLike commentLike) {
        if ( commentLike == null ) {
            return null;
        }

        CommentLikeResponse.CommentLikeResponseBuilder commentLikeResponse = CommentLikeResponse.builder();

        commentLikeResponse.userId( commentLikeUserId( commentLike ) );
        commentLikeResponse.commentId( commentLikeCommentId( commentLike ) );
        commentLikeResponse.userFullName( commentLikeUserFullName( commentLike ) );
        commentLikeResponse.userAvatar( commentLikeUserAvatar( commentLike ) );
        commentLikeResponse.id( commentLike.getId() );
        commentLikeResponse.createdAt( commentLike.getCreatedAt() );

        return commentLikeResponse.build();
    }

    @Override
    public List<CommentLikeResponse> toCommentResponseList(List<CommentLike> commentLikes) {
        if ( commentLikes == null ) {
            return null;
        }

        List<CommentLikeResponse> list = new ArrayList<CommentLikeResponse>( commentLikes.size() );
        for ( CommentLike commentLike : commentLikes ) {
            list.add( toResponse( commentLike ) );
        }

        return list;
    }

    private String commentLikeUserId(CommentLike commentLike) {
        User user = commentLike.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String commentLikeCommentId(CommentLike commentLike) {
        Comment comment = commentLike.getComment();
        if ( comment == null ) {
            return null;
        }
        return comment.getId();
    }

    private String commentLikeUserFullName(CommentLike commentLike) {
        User user = commentLike.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String commentLikeUserAvatar(CommentLike commentLike) {
        User user = commentLike.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }
}
