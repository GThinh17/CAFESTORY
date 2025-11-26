package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.FollowCreateDTO;
import vn.gt.__back_end_javaspring.DTO.FollowResponse;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.FollowService;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;


    @PostMapping
    public RestResponse<FollowResponse> follow(@Valid @RequestBody FollowCreateDTO request) {
        FollowResponse data = followService.follow(request);

        RestResponse<FollowResponse> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Follow successfully");
        res.setData(data);

        return res;
    }

    @GetMapping("/users/{userId}/followers")
    public RestResponse<List<FollowResponse>> getUserFollowers(@PathVariable String userId) {
        List<FollowResponse> data = followService.getUserFollower(userId);

        RestResponse<List<FollowResponse>> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get user followers successfully");
        res.setData(data);

        return res;
    }

    @GetMapping("/users/{userId}/following")
    public RestResponse<List<FollowResponse>> getUserFollowing(@PathVariable String userId) {
        List<FollowResponse> data = followService.getUserFollowing(userId);

        RestResponse<List<FollowResponse>> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get user following successfully");
        res.setData(data);

        return res;
    }

    @GetMapping("/pages/{pageId}/followers")
    public RestResponse<List<FollowResponse>> getPageFollowers(@PathVariable String pageId) {
        List<FollowResponse> data = followService.getPageFollower(pageId);

        RestResponse<List<FollowResponse>> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get page followers successfully");
        res.setData(data);

        return res;
    }


    @DeleteMapping("/users/{userId}/following/{userFollowedId}")
    public RestResponse<Object> deleteFollowingUser(
            @PathVariable String userId,
            @PathVariable String userFollowedId
    ) {
        followService.deletedFollowingUserId(userId, userFollowedId);

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NO_CONTENT.value());
        res.setMessage("Unfollow user successfully");
        res.setData(null);

        return res;
    }


    @DeleteMapping("/users/{userId}/following-page/{pageId}")
    public RestResponse<Object> deleteFollowingPage(
            @PathVariable String userId,
            @PathVariable String pageId
    ) {
        followService.deletedFollowingPageId(userId, pageId);

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NO_CONTENT.value());
        res.setMessage("Unfollow page successfully");
        res.setData(null);

        return res;
    }
}
