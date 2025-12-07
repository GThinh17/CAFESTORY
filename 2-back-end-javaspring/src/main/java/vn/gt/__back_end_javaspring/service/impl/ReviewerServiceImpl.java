package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;

import vn.gt.__back_end_javaspring.exception.ReviewerNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.ReviewerMapper;
import vn.gt.__back_end_javaspring.repository.ReviewerRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.ReviewerService;

import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepository reviewerRepository;
    private final ReviewerMapper reviewerMapper;
    private final UserRepository userRepository;

    @Override
    public void addScore(String reviewerId, Integer score) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));
        reviewer.setTotalScore(reviewer.getTotalScore() + score);
        reviewerRepository.save(reviewer);
    }

    @Override
    public void deleteReviewer(String reviewerId) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

        if(reviewer.getIsDeleted() == true) {
            throw new ReviewerNotFound("Reviewer is already deleted");
        }
        reviewer.setIsDeleted(true);
        reviewerRepository.save(reviewer);

    }

    @Override
    public ReviewerResponse getReviewer(String reviewerId) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

        return reviewerMapper.toResponse(reviewer);
    }

    @Override
    public ReviewerResponse extendReviewer(String reviewerId, ReviewerCreateDTO reviewerCreateDTO) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));



        Integer duration = reviewerCreateDTO.getDuration();
        int extendDays = duration;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime base = reviewer.getExpiredAt();

        if (base == null || base.isBefore(now)) {
            base = now;
        }

        reviewer.setExpiredAt(base.plusDays(extendDays));

        reviewerRepository.save(reviewer);
        return reviewerMapper.toResponse(reviewer);
    }



    @Override
    public boolean isReviewerByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        Boolean result = reviewerRepository.existsByUser_Id(userId);
        return result;
    }

    @Override
    public ReviewerResponse registerReviewer(ReviewerCreateDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Reviewer reviewer = reviewerMapper.toModel(dto);

        if (dto.getDuration() != null && dto.getDuration() > 0) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime baseTime;

            if (reviewer.getExpiredAt() != null && reviewer.getExpiredAt().isAfter(now)) {
                baseTime = reviewer.getExpiredAt();
            } else {
                baseTime = now;
            }
            reviewer.setExpiredAt(baseTime.plusMonths(dto.getDuration()));
        }

        reviewer.setUser(user);

        Reviewer saved = reviewerRepository.save(reviewer);
        return reviewerMapper.toResponse(saved);
    }







}
