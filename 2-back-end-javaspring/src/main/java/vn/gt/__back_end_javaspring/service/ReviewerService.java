package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.entity.Reviewer;

import java.util.List;

public interface ReviewerService {
    boolean isReviewerByUserId(String userId);
    ReviewerResponse registerReviewer(ReviewerCreateDTO reviewer);
    void addScore(String reviewerId, Integer score);
    void deleteReviewer(String reviewerId);
    ReviewerResponse getReviewer(String reviewerId);
    ReviewerResponse extendReviewer(String reviewerId, ReviewerCreateDTO reviewerCreateDTO);
}

