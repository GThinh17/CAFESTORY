package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.CommentCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentResponse;
import vn.gt.__back_end_javaspring.DTO.CommentUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Comment;
import vn.gt.__back_end_javaspring.entity.CommentImage;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-10T16:55:23+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentResponse toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.commentId( comment.getId() );
        commentResponse.blogId( commentBlogId( comment ) );
        commentResponse.parentCommentId( commentParentCommentId( comment ) );
        commentResponse.userId( commentUserId( comment ) );
        commentResponse.userFullName( commentUserFullName( comment ) );
        commentResponse.userAvatar( commentUserAvatar( comment ) );
        commentResponse.commentImageId( commentCommentImageId( comment ) );
        commentResponse.commentImageUrl( commentCommentImageImageUrl( comment ) );
        commentResponse.content( comment.getContent() );
        commentResponse.likesCount( comment.getLikesCount() );
        commentResponse.replyCount( comment.getReplyCount() );
        commentResponse.isEdited( comment.getIsEdited() );
        commentResponse.isDeleted( comment.getIsDeleted() );
        commentResponse.isPin( comment.getIsPin() );
        commentResponse.createdAt( comment.getCreatedAt() );
        commentResponse.updatedAt( comment.getUpdatedAt() );
        commentResponse.deletedAt( comment.getDeletedAt() );
        commentResponse.replies( toResponseList( comment.getReplies() ) );

        return commentResponse.build();
    }

    @Override
    public List<CommentResponse> toResponseList(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponse> list = new ArrayList<CommentResponse>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( toResponse( comment ) );
        }

        return list;
    }

    @Override
    public Comment toModel(CommentCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setBlog( commentCreateDTOToBlog( dto ) );
        comment.setUser( commentCreateDTOToUser( dto ) );
        comment.setParentComment( commentCreateDTOToComment( dto ) );
        comment.setCommentImage( commentCreateDTOToCommentImage( dto ) );
        comment.setContent( dto.getContent() );

        return comment;
    }

    @Override
    public void updateEntity(Comment entity, CommentUpdateDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
        if ( dto.getIsDeleted() != null ) {
            entity.setIsDeleted( dto.getIsDeleted() );
        }
        if ( dto.getIsPin() != null ) {
            entity.setIsPin( dto.getIsPin() );
        }
    }

    private String commentBlogId(Comment comment) {
        Blog blog = comment.getBlog();
        if ( blog == null ) {
            return null;
        }
        return blog.getId();
    }

    private String commentParentCommentId(Comment comment) {
        Comment parentComment = comment.getParentComment();
        if ( parentComment == null ) {
            return null;
        }
        return parentComment.getId();
    }

    private String commentUserId(Comment comment) {
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String commentUserFullName(Comment comment) {
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String commentUserAvatar(Comment comment) {
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }

    private String commentCommentImageId(Comment comment) {
        CommentImage commentImage = comment.getCommentImage();
        if ( commentImage == null ) {
            return null;
        }
        return commentImage.getId();
    }

    private String commentCommentImageImageUrl(Comment comment) {
        CommentImage commentImage = comment.getCommentImage();
        if ( commentImage == null ) {
            return null;
        }
        return commentImage.getImageUrl();
    }

    protected Blog commentCreateDTOToBlog(CommentCreateDTO commentCreateDTO) {
        if ( commentCreateDTO == null ) {
            return null;
        }

        Blog blog = new Blog();

        blog.setId( commentCreateDTO.getBlogId() );

        return blog;
    }

    protected User commentCreateDTOToUser(CommentCreateDTO commentCreateDTO) {
        if ( commentCreateDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( commentCreateDTO.getUserId() );

        return user;
    }

    protected Comment commentCreateDTOToComment(CommentCreateDTO commentCreateDTO) {
        if ( commentCreateDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentCreateDTO.getCommentParentId() );

        return comment;
    }

    protected CommentImage commentCreateDTOToCommentImage(CommentCreateDTO commentCreateDTO) {
        if ( commentCreateDTO == null ) {
            return null;
        }

        CommentImage commentImage = new CommentImage();

        commentImage.setImageUrl( commentCreateDTO.getCommentImageUrl() );

        return commentImage;
    }
}
