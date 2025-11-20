package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.CommentLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentLikeResponse;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.CommentLikeService;

import java.util.List;

@RestController
@RequestMapping("/api/comment-likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("")
    public RestResponse<CommentLikeResponse> likeComment(
            @Valid @RequestBody CommentLikeCreateDTO dto
    ) {
        CommentLikeResponse data = commentLikeService.likeComment(dto);

        RestResponse<CommentLikeResponse> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Like comment successfully");
        res.setData(data);

        return res;
    }


    @DeleteMapping("")
    public RestResponse<Object> unlikeComment(
            @RequestParam String userId,
            @RequestParam String commentId
    ) {
        commentLikeService.unlikeComment(userId, commentId);

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Unlike comment successfully");
        res.setData(null);

        return res;
    }

    @GetMapping("/by-comment")
    public RestResponse<List<CommentLikeResponse>> getLikesByComment(
            @RequestParam String commentId
    ) {
        List<CommentLikeResponse> data = commentLikeService.getLikesByComment(commentId);

        RestResponse<List<CommentLikeResponse>> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Get comment likes successfully");
        res.setData(data);

        return res;
    }

    /**
     * Check user đã like comment chưa
     */
    @GetMapping("/is-liked")
    public RestResponse<Boolean> isLiked(
            @RequestParam String userId,
            @RequestParam String commentId
    ) {
        boolean liked = commentLikeService.isLikedByUser(userId, commentId);

        RestResponse<Boolean> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Check comment like successfully");
        res.setData(liked);

        return res;
    }
}
