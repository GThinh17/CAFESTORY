package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.Follow;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, String> {
    List<Follow> findByFollowerId(String followerId);
}
