package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.FollowCreateDTO;
import vn.gt.__back_end_javaspring.DTO.FollowResponse;
import vn.gt.__back_end_javaspring.entity.Follow;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T22:08:19+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class FollowMapperImpl implements FollowMapper {

    @Override
    public Follow toModel(FollowCreateDTO followCreateDTO) {
        if ( followCreateDTO == null ) {
            return null;
        }

        Follow follow = new Follow();

        follow.setFollowType( followCreateDTO.getFollowType() );

        return follow;
    }

    @Override
    public FollowResponse toResponse(Follow follow) {
        if ( follow == null ) {
            return null;
        }

        FollowResponse.FollowResponseBuilder followResponse = FollowResponse.builder();

        followResponse.followId( follow.getId() );
        followResponse.followerId( followFollowerId( follow ) );
        followResponse.followerFullName( followFollowerFullName( follow ) );
        followResponse.followerAvatar( followFollowerAvatar( follow ) );
        followResponse.userFollowedId( followFollowedUserId( follow ) );
        followResponse.userFollowedFullName( followFollowedUserFullName( follow ) );
        followResponse.userFollowedAvatar( followFollowedUserAvatar( follow ) );
        followResponse.pageFollowedId( followFollowedPageId( follow ) );
        followResponse.pageFollowedName( followFollowedPagePageName( follow ) );
        followResponse.pageFollowedAvatar( followFollowedPageAvatarUrl( follow ) );
        followResponse.createdAt( follow.getCreatedAt() );
        followResponse.followType( follow.getFollowType() );

        return followResponse.build();
    }

    @Override
    public List<FollowResponse> toResponse(List<Follow> follows) {
        if ( follows == null ) {
            return null;
        }

        List<FollowResponse> list = new ArrayList<FollowResponse>( follows.size() );
        for ( Follow follow : follows ) {
            list.add( toResponse( follow ) );
        }

        return list;
    }

    private String followFollowerId(Follow follow) {
        User follower = follow.getFollower();
        if ( follower == null ) {
            return null;
        }
        return follower.getId();
    }

    private String followFollowerFullName(Follow follow) {
        User follower = follow.getFollower();
        if ( follower == null ) {
            return null;
        }
        return follower.getFullName();
    }

    private String followFollowerAvatar(Follow follow) {
        User follower = follow.getFollower();
        if ( follower == null ) {
            return null;
        }
        return follower.getAvatar();
    }

    private String followFollowedUserId(Follow follow) {
        User followedUser = follow.getFollowedUser();
        if ( followedUser == null ) {
            return null;
        }
        return followedUser.getId();
    }

    private String followFollowedUserFullName(Follow follow) {
        User followedUser = follow.getFollowedUser();
        if ( followedUser == null ) {
            return null;
        }
        return followedUser.getFullName();
    }

    private String followFollowedUserAvatar(Follow follow) {
        User followedUser = follow.getFollowedUser();
        if ( followedUser == null ) {
            return null;
        }
        return followedUser.getAvatar();
    }

    private String followFollowedPageId(Follow follow) {
        Page followedPage = follow.getFollowedPage();
        if ( followedPage == null ) {
            return null;
        }
        return followedPage.getId();
    }

    private String followFollowedPagePageName(Follow follow) {
        Page followedPage = follow.getFollowedPage();
        if ( followedPage == null ) {
            return null;
        }
        return followedPage.getPageName();
    }

    private String followFollowedPageAvatarUrl(Follow follow) {
        Page followedPage = follow.getFollowedPage();
        if ( followedPage == null ) {
            return null;
        }
        return followedPage.getAvatarUrl();
    }
}
