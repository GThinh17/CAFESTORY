package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.BlogLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogLikeResponse;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.BlogLike;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T05:07:34+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class BlogLikeMapperImpl implements BlogLikeMapper {

    @Override
    public BlogLike toModel(BlogLikeCreateDTO blogLikeCreateDTO) {
        if ( blogLikeCreateDTO == null ) {
            return null;
        }

        BlogLike blogLike = new BlogLike();

        blogLike.setBlog( blogLikeCreateDTOToBlog( blogLikeCreateDTO ) );
        blogLike.setUser( blogLikeCreateDTOToUser( blogLikeCreateDTO ) );

        return blogLike;
    }

    @Override
    public BlogLikeResponse toResponse(BlogLike bloglikeid) {
        if ( bloglikeid == null ) {
            return null;
        }

        BlogLikeResponse.BlogLikeResponseBuilder blogLikeResponse = BlogLikeResponse.builder();

        blogLikeResponse.userId( bloglikeidUserId( bloglikeid ) );
        blogLikeResponse.blogId( bloglikeidBlogId( bloglikeid ) );
        blogLikeResponse.userFullName( bloglikeidUserFullName( bloglikeid ) );
        blogLikeResponse.userAvatar( bloglikeidUserAvatar( bloglikeid ) );
        blogLikeResponse.id( bloglikeid.getId() );
        blogLikeResponse.createdAt( bloglikeid.getCreatedAt() );

        return blogLikeResponse.build();
    }

    @Override
    public List<BlogLikeResponse> toResponseList(List<BlogLike> bloglikeids) {
        if ( bloglikeids == null ) {
            return null;
        }

        List<BlogLikeResponse> list = new ArrayList<BlogLikeResponse>( bloglikeids.size() );
        for ( BlogLike blogLike : bloglikeids ) {
            list.add( toResponse( blogLike ) );
        }

        return list;
    }

    protected Blog blogLikeCreateDTOToBlog(BlogLikeCreateDTO blogLikeCreateDTO) {
        if ( blogLikeCreateDTO == null ) {
            return null;
        }

        Blog blog = new Blog();

        blog.setId( blogLikeCreateDTO.getBlogId() );

        return blog;
    }

    protected User blogLikeCreateDTOToUser(BlogLikeCreateDTO blogLikeCreateDTO) {
        if ( blogLikeCreateDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( blogLikeCreateDTO.getUserId() );

        return user;
    }

    private String bloglikeidUserId(BlogLike blogLike) {
        User user = blogLike.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String bloglikeidBlogId(BlogLike blogLike) {
        Blog blog = blogLike.getBlog();
        if ( blog == null ) {
            return null;
        }
        return blog.getId();
    }

    private String bloglikeidUserFullName(BlogLike blogLike) {
        User user = blogLike.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String bloglikeidUserAvatar(BlogLike blogLike) {
        User user = blogLike.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }
}
