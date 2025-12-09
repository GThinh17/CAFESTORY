package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.entity.RestResponse;
import vn.gt.__back_end_javaspring.service.PageService;

@RestController
@RequestMapping("/api/pages")
@RequiredArgsConstructor
@Validated
public class PageController {

    private final PageService pageService;

    @PostMapping("")
    public ResponseEntity<PageResponse> createPage(
            @Valid @RequestBody PageCreateDTO request
    ) {
        PageResponse data = pageService.createPage(request);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/{pageId}")
    public ResponseEntity<PageResponse> getPageById(
            @PathVariable String pageId
    ) {
        PageResponse data = pageService.getPageById(pageId);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/cafe-owner/{cafeOwnerId}")
    public ResponseEntity<PageResponse> getPageByCafeOwnerId(
            @PathVariable String cafeOwnerId
    ) {
        PageResponse data = pageService.getPageByCafeOwnerId(cafeOwnerId);
        return ResponseEntity.ok().body(data);
    }

    @PatchMapping("/{pageId}")
    public ResponseEntity<PageResponse> updatePage(
            @PathVariable String pageId,
            @Valid @RequestBody PageUpdateDTO request
    ) {
        PageResponse data = pageService.updatePage(request, pageId);



        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{pageId}")
    public ResponseEntity<Object> deletePage(
            @PathVariable String pageId
    ) {
        pageService.deletePage(pageId);

        return ResponseEntity.ok().build();
    }
}
