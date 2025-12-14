package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.FollowCreateDTO;
import vn.gt.__back_end_javaspring.DTO.FollowResponse;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;
import vn.gt.__back_end_javaspring.entity.Follow;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.FollowType;
import vn.gt.__back_end_javaspring.enums.NotificationType;
import vn.gt.__back_end_javaspring.exception.ExistFollow;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.exception.ReviewerNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.FollowMapper;
import vn.gt.__back_end_javaspring.repository.FollowRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.ReviewerRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.CafeOwnerService;
import vn.gt.__back_end_javaspring.service.FollowService;
import vn.gt.__back_end_javaspring.service.NotificationClient;
import vn.gt.__back_end_javaspring.service.ReviewerService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserRepository userRepository;
    private final PageRepository pageRepository;
    private final ReviewerService reviewerService;
    private final ReviewerRepository reviewerRepository;
    private  final CafeOwnerService cafeOwnerService;
    private final NotificationClient notificationClient;

    @Override
    public FollowResponse follow(FollowCreateDTO request) {

        User follower = userRepository.findById(request.getFollowerId())
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));

        if(cafeOwnerService.isCafeOwner(request.getFollowerId())){
            throw new RuntimeException("Cafe owner can not follow anyone");
        }

        Follow follow = new Follow();

        follow.setFollower(follower);
        follow.setFollowType(request.getFollowType());
        NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
        notificationRequestDTO.setSenderId(follower.getId());

        if (request.getFollowType() == FollowType.USER) {
            User followedUser = userRepository.findById(request.getFollowedUserId())
                    .orElseThrow(() -> new UserNotFoundException("Followed user not found"));

            if (follower.getId().equals(followedUser.getId())) {
                throw new RuntimeException("Cannot follow yourself");
            }

            if (followRepository.existsByFollower_IdAndFollowedUser_Id(
                    request.getFollowerId(), request.getFollowedUserId())) {
                throw new ExistFollow("Follower already exists");
            }


            follower.setFollowingCount(follower.getFollowingCount() + 1);
            followedUser.setFollowerCount(followedUser.getFollowerCount() + 1);

            follow.setFollowedUser(followedUser);
            follow.setFollowedPage(null);
            follow.setFollowedReviewer(null);

            //Notification
            notificationRequestDTO.setReceiverId(followedUser.getId());
            notificationRequestDTO.setType(NotificationType.NEW_FOLLOWER_USER);
            notificationRequestDTO.setPostId(null);
            notificationRequestDTO.setCommentId(null);
            notificationRequestDTO.setPageId(null);
            notificationRequestDTO.setWalletTransactionId(null);
            notificationRequestDTO.setBadgeId(null);
            notificationRequestDTO.setContent(follower.getFullName() + "đã theo dõi bạn");
            notificationClient.sendNotification(notificationRequestDTO);
        } else if (request.getFollowType() == FollowType.PAGE) {
            Page page = pageRepository.findById(request.getFollowedPageId())
                    .orElseThrow(() -> new PageNotFoundException("Page not found"));

            if (followRepository.existsByFollower_IdAndFollowedPage_Id(
                    request.getFollowerId(), request.getFollowedPageId())) {
                throw new ExistFollow("Already followed this page");
            }

            follower.setFollowingCount(follower.getFollowingCount() + 1);
            page.setFollowingCount(page.getFollowingCount() + 1);

            follow.setFollowedPage(page);
            follow.setFollowedUser(null);
            follow.setFollowedReviewer(null);
            //Notification

            notificationRequestDTO.setReceiverId(page.getCafeOwner().getUser().getId());
            notificationRequestDTO.setType(NotificationType.NEW_PAGE_FOLLOWER);
            notificationRequestDTO.setPostId(null);
            notificationRequestDTO.setCommentId(null);
            notificationRequestDTO.setPageId(page.getId());
            notificationRequestDTO.setWalletTransactionId(null);
            notificationRequestDTO.setBadgeId(null);
            notificationRequestDTO.setContent(follower.getFullName() + "đã theo dõi quán cà phê bạn");
            notificationClient.sendNotification(notificationRequestDTO);

        } else if (request.getFollowType() == FollowType.REVIEWER) {

            Reviewer reviewer = reviewerRepository.findByUser_Id(request.getFollowedUserId());

            if (reviewer == null) {
                throw new ReviewerNotFound("Reviewer not found");
            }

            String reviewerId = reviewer.getId();

            if (reviewer.getUser() != null
                    && reviewer.getUser().getId().equals(follower.getId())) {
                throw new RuntimeException("Cannot follow yourself");
            }

            if (followRepository.existsByFollower_IdAndFollowedReviewer_Id(
                    request.getFollowerId(), reviewerId)) {
                throw new ExistFollow("Following Reviewer already exists");
            }



            follower.setFollowingCount(follower.getFollowingCount() + 1);
            reviewer.setFollowerCount(reviewer.getFollowerCount() + 1);
            reviewerRepository.save(reviewer);

            follow.setFollowedReviewer(reviewer);
            follow.setFollowedPage(null);
            follow.setFollowedUser(null);

            //Thieu notification

            notificationRequestDTO.setReceiverId(reviewer.getUser().getId());
            notificationRequestDTO.setType(NotificationType.NEW_FOLLOWER_REVIEWER);
            notificationRequestDTO.setPostId(null);
            notificationRequestDTO.setCommentId(null);
            notificationRequestDTO.setPageId(null);
            notificationRequestDTO.setWalletTransactionId(null);
            notificationRequestDTO.setBadgeId(null);
            notificationRequestDTO.setContent(follower.getFullName() + "đã theo dõi bạn");
            notificationClient.sendNotification(notificationRequestDTO);
        }
        else  {
            throw new IllegalArgumentException("Invalid follow type");
        }
        Follow saved = followRepository.save(follow);
        return followMapper.toResponse(saved);
    }


    // Get all follow user (Ai dang follow user nay)
    @Override
    public List<FollowResponse> getUserFollower(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));
        List<Follow> follows = followRepository.findByFollowedUser_Id(user.getId());
        return followMapper.toResponse(follows);
    }

    // Get all user follow (user nay follow ai)
    @Override
    public List<FollowResponse> getUserFollowing(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));

        List<Follow> follows = followRepository.findByFollower_Id(user.getId());
        return followMapper.toResponse(follows);
    }

    // Get all user follow page
    @Override
    public List<FollowResponse> getPageFollower(String pageId) {
        Page page = pageRepository.findById(pageId)
                .orElseThrow(() -> new PageNotFoundException("Page not found"));

        List<Follow> follows = followRepository.findByFollowedPage_Id(page.getId());
        return followMapper.toResponse(follows);
    }

    @Override
    public void deletedFollowingUserId(String userId, String userFollowedId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Follower not found"));
        User userFollowed = userRepository.findById(userFollowedId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));

        if (!followRepository.existsByFollower_IdAndFollowedUser_Id(userId, userFollowedId)) {
            throw new ExistFollow("Follower not exists");
        }

        if (user.getFollowingCount() != 0 && user.getFollowingCount() > 0) {
            user.setFollowingCount(user.getFollowingCount() - 1);
        }
        if (userFollowed.getFollowerCount() != 0 && userFollowed.getFollowerCount() > 0) {
            userFollowed.setFollowerCount(userFollowed.getFollowerCount() - 1);
        }
        followRepository.deleteByFollower_IdAndFollowedUser_Id(user.getId(), userFollowed.getId());

    }

    @Override
    public void deletedFollowingPageId(String userId, String pageId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Follower not found"));
        Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException("Page not found"));
        if (!followRepository.existsByFollower_IdAndFollowedPage_Id(user.getId(), pageId)) {
            throw new ExistFollow("Follower not exists");
        }

        if (user.getFollowingCount() != 0 && user.getFollowingCount() > 0) {
            user.setFollowingCount(user.getFollowingCount() - 1);
        }
        if(page.getFollowingCount() != 0 && page.getFollowingCount() > 0) {
            page.setFollowingCount(page.getFollowingCount() - 1);
        }

        followRepository.deleteByFollower_IdAndFollowedPage_Id(user.getId(), page.getId());
    }

    @Override
    public Boolean isFollowPage(String userId, String pageId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Follower not found"));
        Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException("Page not found"));

        return followRepository.existsByFollower_IdAndFollowedPage_Id(user.getId(), page.getId());

    }

    @Override
    public Boolean isFollowUser(String userId, String userFollowingId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Follower not found"));
        User user1 = userRepository.findById(userFollowingId).orElseThrow(() -> new UserNotFoundException("Follower not found"));

        return followRepository.existsByFollower_IdAndFollowedUser_Id(user.getId(), user1.getId());
    }

    private final FollowRepository followRepository;
    private final FollowMapper followMapper;

}
