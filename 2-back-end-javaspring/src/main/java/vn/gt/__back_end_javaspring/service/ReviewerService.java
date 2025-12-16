
package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.UserResponseDTO;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;

import java.util.List;

public interface ReviewerService {
    boolean isReviewerByUserId(String userId);
    ReviewerResponse registerReviewer(ReviewerCreateDTO reviewer);
    void addScore(String reviewerId, Integer score);
    void deleteReviewer(String reviewerId);
    ReviewerResponse getReviewer(String reviewerId);
    ReviewerResponse extendReviewer(String reviewerId, ReviewerCreateDTO reviewerCreateDTO);
    ReviewerResponse updateReviewer(String reviewerId, ReviewerUpdateDTO dto);
    List<ReviewerResponse> getReviewersFollowedByUser(String userId);


    List<ReviewerResponse> getReviewersFollowedByUserOrderByFollowerCountDesc(String userId);

    List<ReviewerResponse> getAllReviewersOrderByFollowerCountDesc();
    String getUserId(String reviewerId);

}

