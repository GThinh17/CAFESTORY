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
    public RestResponse<CommentResponse> getCommentById(@PathVariable String id) {
        CommentResponse commentResponse = commentService.getCommentById(id);

        RestResponse<CommentResponse> restResponse = new RestResponse<>();
        restResponse.setData(commentResponse);
        restResponse.setStatusCode(200);
        restResponse.setMessage("Success");

        return restResponse;
    }


    @GetMapping("")
    public RestResponse<CursorPage<CommentResponse>> getNewestCommentsByBlogId(
            @RequestParam String blogId,
            @RequestParam(required = false) String cursor,
            @RequestParam (defaultValue = "20" ) @Min(1) @Max(100) int size
    ) {
        RestResponse<CursorPage<CommentResponse>> restResponse = new RestResponse<>();
        CursorPage<CommentResponse> commentResponses = commentService.getCommentsNewestByBlogId(blogId, cursor, size);
        restResponse.setData(commentResponses);
        restResponse.setStatusCode(200);
        restResponse.setMessage("Success");
        return restResponse;

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<CommentResponse> createComment(
            @RequestBody @Validated CommentCreateDTO commentCreateDTO){
        RestResponse<CommentResponse> restResponse = new RestResponse<>();
        CommentResponse commentResponse = commentService.addComment(commentCreateDTO);

        //Thieu adding 1 for reply count

        restResponse.setData(commentResponse);
        restResponse.setMessage("Success");
        restResponse.setStatusCode(HttpStatus.CREATED.value());

        return restResponse;
    }

    @PatchMapping("/{id}")
    public RestResponse<CommentResponse> updateComment(
            @PathVariable String id,
            @RequestBody @Validated CommentUpdateDTO commentUpdateDTO
    ){
        CommentResponse commentResponse = commentService.updateComment(id, commentUpdateDTO);
        RestResponse<CommentResponse> restResponse = new RestResponse<>();

        restResponse.setData(commentResponse);
        restResponse.setMessage("Success");
        restResponse.setStatusCode(HttpStatus.OK.value());

        return restResponse;
    }


    @DeleteMapping("/{id}")
    public RestResponse<CommentResponse> deleteComment(
            @PathVariable String id
    ){
        RestResponse<CommentResponse> restResponse = new RestResponse<>();

        CommentResponse commentResponse = commentService.deleteComment(id);

        restResponse.setData(commentResponse);
        restResponse.setMessage("Success");
        restResponse.setStatusCode(HttpStatus.OK.value());

        return restResponse;
    }


}
