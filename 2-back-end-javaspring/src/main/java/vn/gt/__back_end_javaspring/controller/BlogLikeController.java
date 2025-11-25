package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.BlogLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogLikeResponse;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.BlogLikeService;

import java.util.List;

@RestController
@RequestMapping("/api/blog-likes")
@RequiredArgsConstructor
public class BlogLikeController {

    private final BlogLikeService blogLikeService;


    @PostMapping("")
    public RestResponse<BlogLikeResponse> likeBlog(
            @Valid @RequestBody BlogLikeCreateDTO request) {
        BlogLikeResponse response = blogLikeService.like(request);

        RestResponse<BlogLikeResponse> res = new RestResponse<>();
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
        blogLikeService.unlike(blogId, userId);

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
        boolean liked = blogLikeService.isLiked(userId, blogId);

        RestResponse<Boolean> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Check like status successfully");
        res.setData(liked);

        return res;
    }


    @GetMapping("/count")
    public RestResponse<Long> countLikes(@RequestParam String blogId) {
        long count = blogLikeService.countLikes(blogId);

        RestResponse<Long> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get like count successfully");
        res.setData(count);

        return res;
    }

    @GetMapping("/by-blog")
    public RestResponse<List<BlogLikeResponse>> getLikes(@RequestParam String blogId) {
        List<BlogLikeResponse> list = blogLikeService.getLikesByBlog(blogId);

        RestResponse<List<BlogLikeResponse>> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get list of likes successfully");
        res.setData(list);

        return res;
    }
}
