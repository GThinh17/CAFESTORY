package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BlogLikeResponse> likeBlog(
            @Valid @RequestBody BlogLikeCreateDTO request) {
        BlogLikeResponse response = blogLikeService.like(request);

        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("")
    public ResponseEntity<Void> unlikeBlog(
            @RequestParam String blogId,
            @RequestParam String userId
    ) {
        blogLikeService.unlike(blogId, userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/is-liked")
    public ResponseEntity<Boolean> isLiked(
            @RequestParam String blogId,
            @RequestParam String userId
    ) {
        boolean liked = blogLikeService.isLiked(userId, blogId);



        return ResponseEntity.ok().body(liked);
    }


    @GetMapping("/count")
    public ResponseEntity<Long> countLikes(@RequestParam String blogId) {
        long count = blogLikeService.countLikes(blogId);


        return ResponseEntity.ok().body(count);
    }

    @GetMapping("/by-blog")
    public ResponseEntity<List<BlogLikeResponse>> getLikes(@RequestParam String blogId) {
        List<BlogLikeResponse> list = blogLikeService.getLikesByBlog(blogId);

        return ResponseEntity.ok().body(list);
    }
}
