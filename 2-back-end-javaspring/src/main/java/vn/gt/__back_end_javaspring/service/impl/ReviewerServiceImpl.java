package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.repository.ReviewerRepository;
import vn.gt.__back_end_javaspring.service.ReviewerService;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepository reviewerRepository;

    @Override
    public boolean isReviewer(String userId) {
        return reviewerRepository.existsById(userId);
    }
}
