package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.ShareCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ShareReponse;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.entity.Share;
import vn.gt.__back_end_javaspring.service.ShareService;

import java.util.List;

@RestController
@RequestMapping("/api/shares")
@Validated
@RequiredArgsConstructor
public class ShareController {
    private final ShareService shareService;

    @PostMapping()
    public RestResponse<ShareReponse> createShare(
            @Valid @RequestBody ShareCreateDTO shareCreateDTO
            ){
        ShareReponse shareReponse = shareService.createShare(shareCreateDTO);
        RestResponse<ShareReponse> response = new RestResponse<>();

        response.setData(shareReponse);
        response.setMessage("Share created successfully");
        response.setStatusCode(200);
        return response;
    }

    @GetMapping("/{id}")
    public RestResponse<ShareReponse> getShareById(@PathVariable String id){
        ShareReponse shareReponse = shareService.getShareById(id);

        RestResponse<ShareReponse> response = new RestResponse<>();
        response.setData(shareReponse);
        response.setMessage("Share found successfully");
        response.setStatusCode(200);
        return response;
    }

    @GetMapping("/by-blog")
    public RestResponse<List<ShareReponse>> getSharesByBlog(
            @RequestParam String blogId){
        List<ShareReponse> data = shareService.getSharesByBlog(blogId);
        RestResponse<List<ShareReponse>> response = new RestResponse<>();

        response.setData(data);
        response.setMessage("Share found successfully");
        response.setStatusCode(200);
        return response;
    }

    @GetMapping("/by-user")
    public RestResponse<List<ShareReponse>> getSharesByUser(
            @RequestParam String userId
    ){
        List<ShareReponse> data = shareService.getSharesByUser(userId);
        RestResponse<List<ShareReponse>> response = new RestResponse<>();

        response.setData(data);
        response.setMessage("Share found successfully");
        response.setStatusCode(200);
        return response;
    }


    @DeleteMapping("/{shareId}")
    public RestResponse<ShareReponse> deleteShare(
            @PathVariable String shareId,
            @RequestParam String userId){
        shareService.softDeleteShare(shareId, userId);
        RestResponse<ShareReponse> response = new RestResponse<>();

        response.setMessage("Share deleted successfully");
        response.setStatusCode(200);
        response.setData(null);
        return response;
    }
}
