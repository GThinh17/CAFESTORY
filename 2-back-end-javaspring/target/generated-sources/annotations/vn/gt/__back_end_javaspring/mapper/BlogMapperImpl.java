package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.BlogCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogResponse;
import vn.gt.__back_end_javaspring.DTO.BlogUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Location;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T22:35:51+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class BlogMapperImpl implements BlogMapper {

    @Override
    public BlogResponse toResponse(Blog blog) {
        if ( blog == null ) {
            return null;
        }

        BlogResponse.BlogResponseBuilder blogResponse = BlogResponse.builder();

        blogResponse.locationId( blogLocationId( blog ) );
        blogResponse.locationName( blogLocationName( blog ) );
        blogResponse.userId( blogUserId( blog ) );
        blogResponse.userFullName( blogUserFullName( blog ) );
        blogResponse.userAvatar( blogUserAvatar( blog ) );
        blogResponse.pageId( blogPageId( blog ) );
        blogResponse.commentCount( blog.getCommentsCount() );
        blogResponse.likeCount( blog.getLikesCount() );
        blogResponse.shareCount( blog.getSharesCount() );
        blogResponse.id( blog.getId() );
        blogResponse.caption( blog.getCaption() );
        blogResponse.visibility( blog.getVisibility() );
        blogResponse.isPin( blog.getIsPin() );
        blogResponse.allowComment( blog.getAllowComment() );
        blogResponse.isDeleted( blog.getIsDeleted() );
        blogResponse.createdAt( blog.getCreatedAt() );
        blogResponse.updatedAt( blog.getUpdatedAt() );

        blogResponse.mediaUrls( blog.getMediaList() == null ? null : blog.getMediaList().stream().map(m -> m.getMediaUrl()).collect(java.util.stream.Collectors.toList()) );

        return blogResponse.build();
    }

    @Override
    public List<BlogResponse> toResponseList(List<Blog> blogs) {
        if ( blogs == null ) {
            return null;
        }

        List<BlogResponse> list = new ArrayList<BlogResponse>( blogs.size() );
        for ( Blog blog : blogs ) {
            list.add( toResponse( blog ) );
        }

        return list;
    }

    @Override
    public Blog toModel(BlogCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Blog blog = new Blog();

        blog.setCaption( dto.getCaption() );
        blog.setVisibility( dto.getVisibility() );

        return blog;
    }

    @Override
    public void updateEntity(Blog entity, BlogUpdateDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCaption() != null ) {
            entity.setCaption( dto.getCaption() );
        }
        if ( dto.getIsPin() != null ) {
            entity.setIsPin( dto.getIsPin() );
        }
        if ( dto.getAllowComment() != null ) {
            entity.setAllowComment( dto.getAllowComment() );
        }
        if ( dto.getVisibility() != null ) {
            entity.setVisibility( dto.getVisibility() );
        }
        if ( dto.getIsDeleted() != null ) {
            entity.setIsDeleted( dto.getIsDeleted() );
        }
    }

    private String blogLocationId(Blog blog) {
        Location location = blog.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getId();
    }

    private String blogLocationName(Blog blog) {
        Location location = blog.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getName();
    }

    private String blogUserId(Blog blog) {
        User user = blog.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String blogUserFullName(Blog blog) {
        User user = blog.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String blogUserAvatar(Blog blog) {
        User user = blog.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }

    private String blogPageId(Blog blog) {
        Page page = blog.getPage();
        if ( page == null ) {
            return null;
        }
        return page.getId();
    }
}
