package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.FollowType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowResponse {
    String followId;

    //Follower
    String followerId;
    String followerFullName;
    String followerAvatar;

    //Followed
    FollowType followType;

    //user
    String userFollowedId;
    String userFollowedFullName;
    String userFollowedAvatar;


    //Page
    String pageFollowedId;
    String pageFollowedName;
    String pageFollowedAvatar;

    LocalDateTime createdAt;


}
