package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.PageAlbumCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageAlbumResponse;
import vn.gt.__back_end_javaspring.DTO.PageAlbumUpdateDTO;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.PageAlbumService;

import java.util.List;

@RestController
@RequestMapping("/api/page-albums")
@RequiredArgsConstructor
@Validated
public class PageAlbumController {

    private final PageAlbumService pageAlbumService;

    @PostMapping("")
    public ResponseEntity<PageAlbumResponse> createPageAlbum(
            @Valid @RequestBody PageAlbumCreateDTO dto
    ) {
        PageAlbumResponse data = pageAlbumService.createPageAlbum(dto);


        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<PageAlbumResponse> getPageAlbumById(
            @PathVariable String albumId
    ) {
        PageAlbumResponse data = pageAlbumService.getPageAlbumById(albumId);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/page/{pageId}")
    public ResponseEntity<List<PageAlbumResponse>> getAlbumsByPageId(
            @PathVariable String pageId
    ) {
        List<PageAlbumResponse> data = pageAlbumService.getAlbumsByPageId(pageId);


        return ResponseEntity.ok().body(data);
    }

    @PatchMapping("/{albumId}")
    public ResponseEntity<PageAlbumResponse> updatePageAlbum(
            @PathVariable String albumId,
            @Valid @RequestBody PageAlbumUpdateDTO dto
    ) {
        PageAlbumResponse data = pageAlbumService.updatePageAlbum(albumId, dto);

        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<PageAlbumResponse> softDeleteAlbum(
            @PathVariable String albumId
    ) {
        PageAlbumResponse data = pageAlbumService.softDeleteAlbum(albumId);

        return ResponseEntity.ok().body(data);
    }


    @PostMapping("/{albumId}/images")
    public ResponseEntity<PageAlbumResponse> addImagesToAlbum(
            @PathVariable String albumId,
            @RequestBody List<String> imageUrls
    ) {
        PageAlbumResponse data = pageAlbumService.addImageToAlbum(albumId, imageUrls);

        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{albumId}/images/{imageId}")
    public ResponseEntity<PageAlbumResponse> removeImageFromAlbum(
            @PathVariable String albumId,
            @PathVariable String imageId
    ) {
        PageAlbumResponse data = pageAlbumService.removeImage(albumId, imageId);

        return ResponseEntity.ok().body(data);
    }
}
