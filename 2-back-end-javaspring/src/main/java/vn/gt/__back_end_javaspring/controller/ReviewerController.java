package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.service.ReviewerService;

import java.util.List;
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

    @GetMapping("/user/{userId}/followed")
    public ResponseEntity<List<ReviewerResponse>> getReviewersFollowedByUser(
            @PathVariable String userId) {
        List<ReviewerResponse> responses = reviewerService.getReviewersFollowedByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}/followed/sort-follower-desc")
    public ResponseEntity<List<ReviewerResponse>> getReviewersFollowedByUserOrderByFollowerCountDesc(
            @PathVariable String userId) {
        List<ReviewerResponse> responses = reviewerService.getReviewersFollowedByUserOrderByFollowerCountDesc(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/top/follower-desc")
    public ResponseEntity<List<ReviewerResponse>> getAllReviewersOrderByFollowerCountDesc() {
        List<ReviewerResponse> responses = reviewerService.getAllReviewersOrderByFollowerCountDesc();
        return ResponseEntity.ok(responses);
    }
}
