package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.BlogCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogResponse;
import vn.gt.__back_end_javaspring.DTO.BlogUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.CursorPage;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.BlogService;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
@Validated
public class BlogController {
    private final BlogService blogService;

    @GetMapping("")
    public ResponseEntity<CursorPage<BlogResponse>> getNewBlogs(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {

        if (userId == null) {
            RestResponse<CursorPage<BlogResponse>> restResponse = new RestResponse<>();
            CursorPage<BlogResponse> data = blogService.findNewestBlog(cursor, size);
            return ResponseEntity.ok().body(data);
        } else {
            RestResponse<CursorPage<BlogResponse>> restResponse = new RestResponse<>();
            CursorPage<BlogResponse> data = blogService.findUserBlog(userId, cursor, size);

            return ResponseEntity.ok().body(data);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(
            @PathVariable String id) {
        RestResponse<BlogResponse> restResponse = new RestResponse<>();
        BlogResponse blogResponse = blogService.getBlogById(id);

        return ResponseEntity.ok().body(blogResponse);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BlogResponse> createBlog(
            @RequestBody @Valid BlogCreateDTO blogCreateDTO) {
        BlogResponse data = blogService.createBlog(blogCreateDTO);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable String id,
            @RequestBody @Valid BlogUpdateDTO blogUpdateDTO) {
        BlogResponse data = blogService.updateBlog(id, blogUpdateDTO);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<org.springframework.data.domain.Page<BlogResponse>> getBlogsForUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        org.springframework.data.domain.Page<BlogResponse> blogPage = blogService.getBlogsForUser(userId, pageRequest);
        return ResponseEntity.ok(blogPage);
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<org.springframework.data.domain.Page<BlogResponse>> getBlogsForReviewer(
            @PathVariable String reviewerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        org.springframework.data.domain.Page<BlogResponse> blogPage = blogService.getBlogsForReviewer(reviewerId, pageRequest);
        return ResponseEntity.ok(blogPage);
    }


    @GetMapping("/page/{pageId}")
    public ResponseEntity<org.springframework.data.domain.Page<BlogResponse>> getBlogsForPage(
            @PathVariable String pageId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BlogResponse> blogPage = blogService.getBlogsForPage(pageId, pageRequest);
        return ResponseEntity.ok(blogPage);
    }



}
