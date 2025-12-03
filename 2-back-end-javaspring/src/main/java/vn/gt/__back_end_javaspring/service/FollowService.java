package vn.gt.__back_end_javaspring.service;


import vn.gt.__back_end_javaspring.DTO.FollowCreateDTO;
import vn.gt.__back_end_javaspring.DTO.FollowResponse;

import java.util.List;

public interface FollowService {
    FollowResponse follow(FollowCreateDTO request); //User followPage

    List<FollowResponse> getUserFollower(String userId); //Lay nhung thang follow user
    List<FollowResponse> getUserFollowing(String userId);//Lay nhung thang User Follow

    List<FollowResponse> getPageFollower(String pageId); //Lya nhung thang follow page page

    void deletedFollowingUserId(String userId, String userFollowingId);
    void deletedFollowingPageId(String userId, String pageId);
}
