package vn.gt.__back_end_javaspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.FollowCreateDTO;
import vn.gt.__back_end_javaspring.DTO.FollowResponse;
import vn.gt.__back_end_javaspring.entity.Follow;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.FollowType;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.FollowMapper;
import vn.gt.__back_end_javaspring.repository.FollowRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.FollowService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final UserRepository userRepository;
    private final PageRepository pageRepository;

    @Override
    public FollowResponse follow(FollowCreateDTO request) {
        Follow follow = new Follow();
        User user = userRepository.findById(request.getFollowerId())
                .orElseThrow(()-> new UserNotFoundException("Follower not found"));

        follow.setFollower(user);

        if(request.getFollowType() == FollowType.USER){
            User userFollow = userRepository.findById(request.getFollowerId())
                    .orElseThrow(()-> new UserNotFoundException("Follower not found"));
            follow.setFollowedUser(userFollow);
            follow.setFollowedPage(null);
        } else{
            Page page = pageRepository.findById(request.getFollowedPageId())
                    .orElseThrow(()-> new PageNotFoundException("Page not found"));
            follow.setFollowedPage(page);
            follow.setFollowedUser(null);
        }

        Follow saved = followRepository.save(follow);
        return followMapper.toResponse(saved);
    }

    //Get all the u
    @Override
    public List<FollowResponse> getUserFollower(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("Follower not found"));
        List<Follow> follows = followRepository.findByFollowerId(userId);
        return followMapper.toResponse(follows);
    }

    @Override
    public List<FollowResponse> getUserFollowing(String userId) {
        return List.of();
    }

    @Override
    public List<FollowResponse> getPageFollower(String pageId) {
        return List.of();
    }

    @Override
    public void deleteFollowed(String userId, String followdId) {

    }

    private final FollowRepository followRepository;
    private final FollowMapper followMapper;


}
