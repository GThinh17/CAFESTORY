package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.LikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.LikeResponse;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.LikeService;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;


    @PostMapping("")
    public RestResponse<LikeResponse> likeBlog(@Valid @RequestBody LikeCreateDTO request) {
        LikeResponse response = likeService.like(request);

        RestResponse<LikeResponse> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Liked blog successfully");
        res.setData(response);

        return res;
    }


    @DeleteMapping("")
    public RestResponse<Void> unlikeBlog(
            @RequestParam String blogId,
            @RequestParam String userId
    ) {
        likeService.unlike(blogId, userId);

        RestResponse<Void> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Unliked blog successfully");
        res.setData(null);

        return res;
    }

    @GetMapping("/is-liked")
    public RestResponse<Boolean> isLiked(
            @RequestParam String blogId,
            @RequestParam String userId
    ) {
        boolean liked = likeService.isLiked(userId, blogId);

        RestResponse<Boolean> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Check like status successfully");
        res.setData(liked);

        return res;
    }


    @GetMapping("/count")
    public RestResponse<Long> countLikes(@RequestParam String blogId) {
        long count = likeService.countLikes(blogId);

        RestResponse<Long> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get like count successfully");
        res.setData(count);

        return res;
    }

    @GetMapping("/by-blog")
    public RestResponse<List<LikeResponse>> getLikes(@RequestParam String blogId) {
        List<LikeResponse> list = likeService.getLikesByBlog(blogId);

        RestResponse<List<LikeResponse>> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get list of likes successfully");
        res.setData(list);

        return res;
    }
}
