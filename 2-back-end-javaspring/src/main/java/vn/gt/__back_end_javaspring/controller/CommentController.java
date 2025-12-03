package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.CommentCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentResponse;
import vn.gt.__back_end_javaspring.DTO.CommentUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.CursorPage;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.CommentService;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable String id) {
        CommentResponse commentResponse = commentService.getCommentById(id);

        return ResponseEntity.ok().body(commentResponse);
    }


    @GetMapping("")
    public ResponseEntity<CursorPage<CommentResponse>> getNewestCommentsByBlogId(
            @RequestParam String blogId,
            @RequestParam(required = false) String cursor,
            @RequestParam (defaultValue = "20" ) @Min(1) @Max(100) int size
    ) {
        CursorPage<CommentResponse> commentResponses = commentService.getCommentsNewestByBlogId(blogId, cursor, size);

        return ResponseEntity.ok().body(commentResponses);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody @Validated CommentCreateDTO commentCreateDTO){
        RestResponse<CommentResponse> restResponse = new RestResponse<>();
        CommentResponse commentResponse = commentService.addComment(commentCreateDTO);

        //Thieu adding 1 for reply count
        return ResponseEntity.ok().body(commentResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable String id,
            @RequestBody @Validated CommentUpdateDTO commentUpdateDTO
    ){
        CommentResponse commentResponse = commentService.updateComment(id, commentUpdateDTO);

        return ResponseEntity.ok().body(commentResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CommentResponse> deleteComment(
            @PathVariable String id
    ){

        CommentResponse commentResponse = commentService.deleteComment(id);



        return ResponseEntity.ok().body(commentResponse);
    }


}
