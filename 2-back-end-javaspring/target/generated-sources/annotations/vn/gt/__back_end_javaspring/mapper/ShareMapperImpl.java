package vn.gt.__back_end_javaspring.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.ShareCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ShareReponse;
import vn.gt.__back_end_javaspring.DTO.ShareUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Location;
import vn.gt.__back_end_javaspring.entity.Share;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T18:36:44+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class ShareMapperImpl implements ShareMapper {

    @Override
    public Share toEntity(ShareCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Share share = new Share();

        share.setCaption( dto.getCaption() );

        return share;
    }

    @Override
    public ShareReponse toResponse(Share share) {
        if ( share == null ) {
            return null;
        }

        ShareReponse.ShareReponseBuilder shareReponse = ShareReponse.builder();

        shareReponse.shareId( share.getId() );
        shareReponse.userId( shareUserId( share ) );
        shareReponse.userFullName( shareUserFullName( share ) );
        shareReponse.userAvatarUrl( shareUserAvatar( share ) );
        shareReponse.blogId( shareBlogId( share ) );
        shareReponse.blogCaption( shareBlogCaption( share ) );
        shareReponse.blogCreatedAt( shareBlogCreatedAt( share ) );
        shareReponse.blogIsDeleted( shareBlogIsDeleted( share ) );
        shareReponse.locationId( shareLocationId( share ) );
        shareReponse.locationName( shareLocationName( share ) );
        shareReponse.addressLine( shareLocationAddressLine( share ) );
        shareReponse.ward( shareLocationWard( share ) );
        shareReponse.province( shareLocationProvince( share ) );
        shareReponse.longitude( shareLocationLongitude( share ) );
        shareReponse.latitude( shareLocationLatitude( share ) );
        shareReponse.caption( share.getCaption() );
        shareReponse.createdAt( share.getCreatedAt() );
        shareReponse.isDeleted( share.getIsDeleted() );

        return shareReponse.build();
    }

    @Override
    public List<ShareReponse> toResponseList(List<Share> shareList) {
        if ( shareList == null ) {
            return null;
        }

        List<ShareReponse> list = new ArrayList<ShareReponse>( shareList.size() );
        for ( Share share : shareList ) {
            list.add( toResponse( share ) );
        }

        return list;
    }

    @Override
    public void updateEntity(ShareUpdateDTO dto, Share share) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCaption() != null ) {
            share.setCaption( dto.getCaption() );
        }
        if ( dto.getIsDeleted() != null ) {
            share.setIsDeleted( dto.getIsDeleted() );
        }
    }

    private String shareUserId(Share share) {
        User user = share.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }

    private String shareUserFullName(Share share) {
        User user = share.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String shareUserAvatar(Share share) {
        User user = share.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatar();
    }

    private String shareBlogId(Share share) {
        Blog blog = share.getBlog();
        if ( blog == null ) {
            return null;
        }
        return blog.getId();
    }

    private String shareBlogCaption(Share share) {
        Blog blog = share.getBlog();
        if ( blog == null ) {
            return null;
        }
        return blog.getCaption();
    }

    private LocalDateTime shareBlogCreatedAt(Share share) {
        Blog blog = share.getBlog();
        if ( blog == null ) {
            return null;
        }
        return blog.getCreatedAt();
    }

    private Boolean shareBlogIsDeleted(Share share) {
        Blog blog = share.getBlog();
        if ( blog == null ) {
            return null;
        }
        return blog.getIsDeleted();
    }

    private String shareLocationId(Share share) {
        Location location = share.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getId();
    }

    private String shareLocationName(Share share) {
        Location location = share.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getName();
    }

    private String shareLocationAddressLine(Share share) {
        Location location = share.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getAddressLine();
    }

    private String shareLocationWard(Share share) {
        Location location = share.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getWard();
    }

    private String shareLocationProvince(Share share) {
        Location location = share.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getProvince();
    }

    private String shareLocationLongitude(Share share) {
        Location location = share.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getLongitude();
    }

    private String shareLocationLatitude(Share share) {
        Location location = share.getLocation();
        if ( location == null ) {
            return null;
        }
        return location.getLatitude();
    }
}
