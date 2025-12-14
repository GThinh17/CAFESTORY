package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<FollowResponse> follow(@Valid @RequestBody FollowCreateDTO request) {
        FollowResponse data = followService.follow(request);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<List<FollowResponse>> getUserFollowers(@PathVariable String userId) {
        List<FollowResponse> data = followService.getUserFollower(userId);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/users/{userId}/following")
    public ResponseEntity<List<FollowResponse>> getUserFollowing(@PathVariable String userId) {
        List<FollowResponse> data = followService.getUserFollowing(userId);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/pages/{pageId}/followers")
    public ResponseEntity<List<FollowResponse>> getPageFollowers(@PathVariable String pageId) {
        List<FollowResponse> data = followService.getPageFollower(pageId);

        return ResponseEntity.ok().body(data);
    }


    @DeleteMapping("/users/{userId}/following/{userFollowedId}")
    public ResponseEntity<Object> deleteFollowingUser(
            @PathVariable String userId,
            @PathVariable String userFollowedId
    ) {
        followService.deletedFollowingUserId(userId, userFollowedId);



        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{userId}/following/{userFollowedId}")
    public ResponseEntity<Boolean> isFollowingUser(
            @PathVariable String userId,
            @PathVariable String userFollowedId){
        Boolean result = followService.isFollowUser(userId, userFollowedId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users/{userId}/following-page/{pageId}")
    public ResponseEntity<Boolean> getUserFollowingPage(
            @PathVariable String userId,
            @PathVariable String pageId
    ){
        Boolean result = followService.isFollowPage(
                userId,
                pageId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/users/{userId}/following-page/{pageId}")
    public ResponseEntity<Object> deleteFollowingPage(
            @PathVariable String userId,
            @PathVariable String pageId
    ) {
        followService.deletedFollowingPageId(userId, pageId);


        return ResponseEntity.ok().build();
    }


}
