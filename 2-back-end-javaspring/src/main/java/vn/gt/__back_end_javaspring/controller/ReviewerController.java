package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.service.ReviewerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/reviewers")
@RequiredArgsConstructor
public class ReviewerController {

    private final ReviewerService reviewerService;

    @PostMapping
    public ResponseEntity<ReviewerResponse> registerReviewer(
            @Valid @RequestBody ReviewerCreateDTO dto) {
        ReviewerResponse response = reviewerService.registerReviewer(dto);
        System.out.println(">>>>>>>>>>>>>>>>RESPONSE NÈ<<<<<<<<<<<<<" + response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> isReviewer(@PathVariable String userId) {
        boolean exists = reviewerService.isReviewerByUserId(userId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/{userId}/score")
    public ResponseEntity<Void> addScore(
            @PathVariable String userId,
            @RequestParam Integer score) {
        reviewerService.addScore(userId, score);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reviewerId}")
    public ResponseEntity<ReviewerResponse> getReviewer(@PathVariable String reviewerId) {
        ReviewerResponse response = reviewerService.getReviewer(reviewerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewerId}")
    public ResponseEntity<Void> deleteReviewer(@PathVariable String reviewerId) {
        reviewerService.deleteReviewer(reviewerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{reviewerId}/extend")
    public ResponseEntity<ReviewerResponse> extendReviewer(
            @PathVariable String reviewerId,
            @RequestBody ReviewerCreateDTO dto) {
        ReviewerResponse response = reviewerService.extendReviewer(reviewerId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> GetAllReviwer() {
        return ResponseEntity.ok().body(this.reviewerService.getAllReviewer());
    }

}
