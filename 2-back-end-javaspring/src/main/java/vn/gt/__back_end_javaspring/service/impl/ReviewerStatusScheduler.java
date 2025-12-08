package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.enums.ReviewerStatus;
import vn.gt.__back_end_javaspring.repository.ReviewerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewerStatusScheduler {
    private final ReviewerRepository reviewerRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredReviewers() {
        LocalDateTime now = LocalDateTime.now();
        List<Reviewer> reviewers = reviewerRepository.findAll();
        for (Reviewer reviewer : reviewers) {
            if(reviewer.getExpiredAt() != null
            && reviewer.getExpiredAt().isBefore(now)
            && reviewer.getStatus() == ReviewerStatus.ACTIVE) {
                reviewer.setStatus(ReviewerStatus.EXPIRED);
            }
        }
    }
}
