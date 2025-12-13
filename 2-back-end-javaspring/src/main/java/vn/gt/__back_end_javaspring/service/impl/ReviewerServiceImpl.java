
package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.entity.Embedded.UserRoleId;
import vn.gt.__back_end_javaspring.enums.FollowType;
import vn.gt.__back_end_javaspring.enums.ReviewerStatus;
import vn.gt.__back_end_javaspring.enums.RoleType;
import vn.gt.__back_end_javaspring.exception.ReviewerNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.PageMapper;
import vn.gt.__back_end_javaspring.mapper.ReviewerMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.ReviewerService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepository reviewerRepository;
    private final ReviewerMapper reviewerMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final FollowRepository followRepository;
    private final PageMapper pageMapper;

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
                .orElseThrow(()-> new ReviewerNotFound("Reviewer not found"));

        reviewerMapper.updateEntity(dto, reviewer);
        reviewerRepository.save(reviewer);
        return reviewerMapper.toResponse(reviewer);
    }

    @Override
    public List<ReviewerResponse> getReviewersFollowedByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        List<Follow> follows = followRepository.findAllByFollower_IdAndFollowType(userId, FollowType.USER);

        return follows.stream()
                .map(Follow::getFollowedUser)
                .filter(Objects::nonNull)
                .map(userFollowed -> reviewerRepository.findByUser_Id(userFollowed.getId()))
                .filter(Objects::nonNull)
                .map(reviewerMapper::toResponse)
                .collect(Collectors.toList());


    }

    @Override
    public List<ReviewerResponse> getReviewersFollowedByUserOrderByFollowerCountDesc(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<ReviewerResponse> reviewerResponses = this.getReviewersFollowedByUser(userId);

        return reviewerResponses.stream()
                .sorted(Comparator.comparing(ReviewerResponse::getFollowerCount).reversed())
                .collect(Collectors.toList());
    }


    @Override
    public List<ReviewerResponse> getAllReviewersOrderByFollowerCountDesc() {
        List<Reviewer> reviewers = reviewerRepository.findAllOrderByFollowerCountDesc();
        if (reviewers.isEmpty()) {
            throw new ReviewerNotFound("Reviewer not found");
        }
        return reviewerMapper.toResponseList(reviewers);
    }

    @Override
    public String getUserId(String reviewerId) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

        String userId =  reviewer.getUser().getId();
        return userId;
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
        //Create Record in UserRole
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Role role = roleRepository.findByroleName(RoleType.REVIEWER);
        
        // if(isReviewerByUserId(dto.getUserId())){

        //     extendReviewer()
        // }

        UserRoleId userRoleId = new UserRoleId(user.getId(), role.getId());
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUser(user);
        userRole.setRole(role);

        userRoleRepository.save(userRole);

        //Create Record in Reviewer
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



