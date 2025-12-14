package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.Follow;
import vn.gt.__back_end_javaspring.enums.FollowType;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, String> {
    List<Follow> findByFollower_Id(String followerId);

    List<Follow> findByFollowedUser_Id(String followedUserId);

    List<Follow> findByFollowedPage_Id(String followedPageId);

    void deleteByFollower_IdAndFollowedUser_Id(String followerId, String followedUserId);

    void deleteByFollower_IdAndFollowedPage_Id(String userId, String pageId);

    boolean existsByFollower_IdAndFollowedUser_Id(String followerId, String followedUserId);

    boolean existsByFollower_IdAndFollowedReviewer_Id(String followerId, String followedReviewerId);

    boolean existsByFollower_IdAndFollowedPage_Id(String followerId, String followedPageId);

    List<Follow> findAllByFollower_IdAndFollowType(String followerId, FollowType followType);
}
