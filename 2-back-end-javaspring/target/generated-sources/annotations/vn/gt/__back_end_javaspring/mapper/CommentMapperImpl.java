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
    date = "2025-12-09T16:11:28+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentResponse toResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setCommentId( comment.getId() );
        commentResponse.setBlogId( commentBlogId( comment ) );
        commentResponse.setParentCommentId( commentParentCommentId( comment ) );
        commentResponse.setUserId( commentUserId( comment ) );
        commentResponse.setUserFullName( commentUserFullName( comment ) );
        commentResponse.setUserAvatar( commentUserAvatar( comment ) );
        commentResponse.setCommentImageId( commentCommentImageId( comment ) );
        commentResponse.setCommentImageUrl( commentCommentImageImageUrl( comment ) );
        commentResponse.setContent( comment.getContent() );
        commentResponse.setLikesCount( comment.getLikesCount() );
        commentResponse.setReplyCount( comment.getReplyCount() );
        commentResponse.setIsEdited( comment.getIsEdited() );
        commentResponse.setIsDeleted( comment.getIsDeleted() );
        commentResponse.setIsPin( comment.getIsPin() );
        commentResponse.setCreatedAt( comment.getCreatedAt() );
        commentResponse.setUpdatedAt( comment.getUpdatedAt() );
        commentResponse.setDeletedAt( comment.getDeletedAt() );
        commentResponse.setReplies( toResponseList( comment.getReplies() ) );

        return commentResponse;
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
