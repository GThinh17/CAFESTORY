package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentLikeResponse> likeComment(
            @Valid @RequestBody CommentLikeCreateDTO dto) {
        CommentLikeResponse data = commentLikeService.likeComment(dto);

        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("")
    public ResponseEntity<Object> unlikeComment(
            @RequestParam String userId,
            @RequestParam String commentId) {
        commentLikeService.unlikeComment(userId, commentId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-comment")
    public ResponseEntity<List<CommentLikeResponse>> getLikesByComment(
            @RequestParam String commentId) {
        List<CommentLikeResponse> data = commentLikeService.getLikesByComment(commentId);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/is-liked")
    public ResponseEntity<Boolean> isLiked(
            @RequestParam String userId,
            @RequestParam String commentId) {
        boolean liked = commentLikeService.isLikedByUser(userId, commentId);

        return ResponseEntity.ok().body(liked);
    }
}
