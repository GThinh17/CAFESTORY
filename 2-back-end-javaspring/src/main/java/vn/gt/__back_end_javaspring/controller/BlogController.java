package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public RestResponse<CursorPage<BlogResponse>> getNewBlogs(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ){

        if(userId == null){
            RestResponse<CursorPage<BlogResponse>> restResponse = new RestResponse<>();
            CursorPage<BlogResponse> data = blogService.findNewestBlog(cursor, size);
            restResponse.setStatusCode(200);
            restResponse.setData(data);
            restResponse.setMessage("success");
            return restResponse;
        } else{
            RestResponse<CursorPage<BlogResponse>> restResponse = new RestResponse<>();
            CursorPage<BlogResponse> data = blogService.findUserBlog(userId, cursor, size);
            restResponse.setStatusCode(200);
            restResponse.setData(data);
            restResponse.setMessage("success");
            return restResponse;
        }

    }

    @GetMapping("/{id}")
    public RestResponse<BlogResponse> getBlogById(
            @PathVariable String id
    ){
        RestResponse<BlogResponse> restResponse = new RestResponse<>();
        BlogResponse blogResponse = blogService.getBlogById(id);

        restResponse.setStatusCode(200);
        restResponse.setData(blogResponse);
        restResponse.setMessage("success");

        return restResponse;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse<BlogResponse> createBlog(
            @RequestBody @Valid BlogCreateDTO blogCreateDTO){
        RestResponse<BlogResponse> restResponse = new RestResponse<>();
        BlogResponse data = blogService.createBlog(blogCreateDTO);
        restResponse.setStatusCode(201);
        restResponse.setMessage("success");
        restResponse.setData(data);
        return restResponse;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable String id) {
        blogService.deleteBlog(id);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestResponse<BlogResponse> updateBlog(
            @PathVariable String id,
            @RequestBody @Valid BlogUpdateDTO blogUpdateDTO
    ){
        RestResponse<BlogResponse> restResponse = new RestResponse<>();
        BlogResponse data = blogService.updateBlog(id, blogUpdateDTO);
        restResponse.setStatusCode(200);
        restResponse.setMessage("success");
        restResponse.setData(data);
        return restResponse;
    }




}
