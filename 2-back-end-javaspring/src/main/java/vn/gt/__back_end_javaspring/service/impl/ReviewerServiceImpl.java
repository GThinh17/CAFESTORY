package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Embedded.UserRoleId;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.Role;
import vn.gt.__back_end_javaspring.entity.User;

import vn.gt.__back_end_javaspring.entity.UserRole;
import vn.gt.__back_end_javaspring.enums.ReviewerStatus;
import vn.gt.__back_end_javaspring.enums.RoleType;
import vn.gt.__back_end_javaspring.exception.ConflictRole;
import vn.gt.__back_end_javaspring.exception.ReviewerNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.ReviewerMapper;
import vn.gt.__back_end_javaspring.repository.ReviewerRepository;
import vn.gt.__back_end_javaspring.repository.RoleRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.repository.UserRoleRepository;
import vn.gt.__back_end_javaspring.service.CafeOwnerService;
import vn.gt.__back_end_javaspring.service.ReviewerService;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepository reviewerRepository;
    private final ReviewerMapper reviewerMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public void addScore(String reviewerId, Integer score) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));
        int currentScore = reviewer.getTotalScore() == null ? 0 : reviewer.getTotalScore();
        reviewer.setTotalScore(currentScore + score);

        reviewerRepository.save(reviewer);
    }

    @Override
    public void deleteReviewer(String reviewerId) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

        if (Boolean.TRUE.equals(reviewer.getIsDeleted())) {
            throw new ReviewerNotFound("Reviewer is already deleted");
        }
        reviewer.setIsDeleted(true);
        reviewerRepository.save(reviewer);

    }

    public List<ReviewerResponse> getAllReviewer() {
        return this.reviewerRepository.findAll()
                .stream()
                .map(reviewer -> ReviewerResponse.builder()
                        .id(reviewer.getId())
                        .userId(reviewer.getUser().getId())
                        .userName(reviewer.getUser().getFullName())
                        .userAvatarUrl(reviewer.getUser().getAvatar())
                        .userEmail(reviewer.getUser().getEmail())
                        .bio(reviewer.getBio())
                        .followerCount(reviewer.getUser().getFollowerCount())
                        .joinAt(reviewer.getJoinAt())
                        .expiredAt(reviewer.getExpiredAt())
                        .totalScore(reviewer.getTotalScore())
                        .isDeleted(reviewer.getIsDeleted())
                        .status(reviewer.getStatus().name())
                        .build())
                .toList();
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
        int extendMonths = duration;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime base = reviewer.getExpiredAt();

        if (base == null || base.isBefore(now)) {
            base = now;
        }

        reviewer.setExpiredAt(base.plusMonths(extendMonths));

        reviewerRepository.save(reviewer);
        return reviewerMapper.toResponse(reviewer);
    }

    @Override
    public ReviewerResponse updateReviewer(String reviewerId, ReviewerUpdateDTO dto) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

        reviewerMapper.updateEntity(dto, reviewer);
        reviewerRepository.save(reviewer);
        return reviewerMapper.toResponse(reviewer);
    }

    @Override
    public boolean isReviewerByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Boolean result = reviewerRepository.existsByUser_Id(userId);
        return result;
    }

    @Override
    public ReviewerResponse registerReviewer(ReviewerCreateDTO dto) {
        // Create Record in UserRole
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Role role = roleRepository.findByroleName(RoleType.REVIEWER);

        UserRoleId userRoleId = new UserRoleId(user.getId(), role.getId());
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUser(user);
        userRole.setRole(role);

        userRoleRepository.save(userRole);

        // Create Record in Reviewer
        Reviewer reviewer = reviewerMapper.toModel(dto);

        if (dto.getDuration() != null && dto.getDuration() > 0) {
            LocalDateTime now = LocalDateTime.now();
            reviewer.setExpiredAt(now.plusMonths(dto.getDuration()));
        }

        reviewer.setStatus(ReviewerStatus.ACTIVE);
        reviewer.setUser(user);

        Reviewer saved = reviewerRepository.save(reviewer);
        return reviewerMapper.toResponse(saved);
    }

}
