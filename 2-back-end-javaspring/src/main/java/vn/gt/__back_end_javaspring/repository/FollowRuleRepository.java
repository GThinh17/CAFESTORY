package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.gt.__back_end_javaspring.entity.FollowRule;

import java.util.Optional;

public interface FollowRuleRepository extends JpaRepository<FollowRule, Long> {

    @Query("""
        SELECT r FROM FollowRule r
        WHERE r.isActive = true
          AND :followCount >= r.minFollow
          AND (r.maxFollow IS NULL OR :followCount <= r.maxFollow)
    """)
    Optional<FollowRule> findMatchingRule(
            @Param("followCount") Long followCount);
}

