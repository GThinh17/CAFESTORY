package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ShareReponse> createShare(
            @Valid @RequestBody ShareCreateDTO shareCreateDTO
            ){
        ShareReponse shareReponse = shareService.createShare(shareCreateDTO);


        return ResponseEntity.ok().body(shareReponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShareReponse> getShareById(@PathVariable String id){
        ShareReponse shareReponse = shareService.getShareById(id);

        return ResponseEntity.ok().body(shareReponse);
    }

    @GetMapping("/by-blog")
    public ResponseEntity<List<ShareReponse>> getSharesByBlog(
            @RequestParam String blogId){
        List<ShareReponse> data = shareService.getSharesByBlog(blogId);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<ShareReponse>> getSharesByUser(
            @RequestParam String userId
    ){
        List<ShareReponse> data = shareService.getSharesByUser(userId);
        return ResponseEntity.ok().body(data);
    }


    @DeleteMapping("/{shareId}")
    public ResponseEntity<ShareReponse> deleteShare(
            @PathVariable String shareId,
            @RequestParam String userId){
        shareService.softDeleteShare(shareId, userId);

        return ResponseEntity.ok().build();
    }
}
