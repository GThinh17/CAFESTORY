package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.FollowCreateDTO;
import vn.gt.__back_end_javaspring.DTO.FollowResponse;
import vn.gt.__back_end_javaspring.entity.Follow;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.FollowType;
import vn.gt.__back_end_javaspring.exception.ExistFollow;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.FollowMapper;
import vn.gt.__back_end_javaspring.repository.FollowRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.FollowService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserRepository userRepository;
    private final PageRepository pageRepository;

    @Override
    public FollowResponse follow(FollowCreateDTO request) {
        Follow follow = new Follow();
        User user = userRepository.findById(request.getFollowerId())
                .orElseThrow(() -> new UserNotFoundException("Follower not found"));

        user.setFollowingCount(user.getFollowingCount() + 1);

        follow.setFollower(user);
        follow.setFollowType(request.getFollowType());
        System.out.println("Follow: " + follow.getFollower().getEmail());
        if (request.getFollowType() == FollowType.USER) {
            User userFollow = userRepository.findById(request.getFollowedUserId())
                    .orElseThrow(() -> new UserNotFoundException("Follower not found"));

            if (followRepository.existsByFollower_IdAndFollowedUser_Id(request.getFollowerId(),
                    request.getFollowedUserId())) {
                throw new ExistFollow("Follower already exists");
            }

            userFollow.setFollowerCount(userFollow.getFollowerCount() + 1);

            if (user.getId().equals(userFollow.getId())) {
                throw new RuntimeException("Cannot follow yourself");
            }
            follow.setFollowedUser(userFollow);
            follow.setFollowedPage(null);
            System.out.println("Followed: " + follow.getFollowedUser().getEmail());

        } else {
            Page page = pageRepository.findById(request.getFollowedPageId())
                    .orElseThrow(() -> new PageNotFoundException("Page not found"));
            page.setFollowersCount(page.getFollowersCount() + 1);
            follow.setFollowedPage(page);
            follow.setFollowedUser(null);
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
        if (user.getFollowingCount() != 0 && user.getFollowingCount() > 0) {
            user.setFollowingCount(user.getFollowingCount() - 1);
        }
        if (userFollowed.getFollowerCount() != 0 && userFollowed.getFollowerCount() > 0) {
            user.setFollowerCount(user.getFollowerCount() - 1);
        }
        user.setFollowerCount(user.getFollowerCount() - 1);
        if (!followRepository.existsByFollower_IdAndFollowedUser_Id(userId, userFollowedId)) {
            throw new ExistFollow("Follower not exists");
        }
        followRepository.deleteByFollower_IdAndFollowedUser_Id(user.getId(), userFollowed.getId());

    }

    @Override
    public void deletedFollowingPageId(String userId, String pageId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Follower not found"));
        Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException("Page not found"));
        if (user.getFollowingCount() != 0 && user.getFollowingCount() > 0) {
            user.setFollowingCount(user.getFollowingCount() - 1);
        }
        if (page.getFollowersCount() != 0 && user.getFollowingCount() > 0) {
            page.setFollowersCount(page.getFollowersCount() - 1);
        }
        if (!followRepository.existsByFollower_IdAndFollowedPage_Id(user.getId(), pageId)) {
            throw new ExistFollow("Follower not exists");
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
