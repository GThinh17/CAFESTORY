package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.service.PageService;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
@RequiredArgsConstructor
@Validated
public class PageController {

    private final PageService pageService;

    @PostMapping("")
    public ResponseEntity<PageResponse> createPage(
            @Valid @RequestBody PageCreateDTO request) {
        PageResponse data = pageService.createPage(request);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/{pageId}")
    public ResponseEntity<PageResponse> getPageById(
            @PathVariable String pageId) {
        PageResponse data = pageService.getPageById(pageId);

        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/cafe-owner/{cafeOwnerId}")
    public ResponseEntity<PageResponse> getPageByCafeOwnerId(
            @PathVariable String cafeOwnerId) {
        PageResponse data = pageService.getPageByCafeOwnerId(cafeOwnerId);
        return ResponseEntity.ok().body(data);
    }

    @PatchMapping("/{pageId}")
    public ResponseEntity<PageResponse> updatePage(
            @PathVariable String pageId,
            @Valid @RequestBody PageUpdateDTO request) {
        PageResponse data = pageService.updatePage(request, pageId);

        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping("/{pageId}")
    public ResponseEntity<Object> deletePage(
            @PathVariable String pageId) {
        pageService.deletePage(pageId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/top-followers")
    public ResponseEntity<List<PageResponse>> getAllPagesOrderByFollowersDesc() {
        List<PageResponse> data = pageService.getAllPagesOrderByFollowersDesc();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user/{userId}/following")
    public ResponseEntity<List<PageResponse>> getAllPagesFollowedByUser(
            @PathVariable String userId) {
        List<PageResponse> data = pageService.getAllPagesFollowedByUser(userId);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/user/{userId}/following/sorted-asc")
    public ResponseEntity<List<PageResponse>> getAllPagesFollowedByUserSortedAsc(
            @PathVariable String userId) {
        List<PageResponse> data = pageService.getAllPagesFollowedByUserSortedAsc(userId);
        return ResponseEntity.ok(data);
    }

    @GetMapping("cafe-owner")
    public ResponseEntity<String> getCafeOwnerId(
            @RequestParam String pageId) {
        String data = pageService.getCafeOwnerId(pageId);
        return ResponseEntity.ok(data);
    }

}
