package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.gt.__back_end_javaspring.entity.EarningEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface EarningEventRepository extends JpaRepository<EarningEvent, String> {
    EarningEvent findEarningEventByLikeId(String likeId);
    EarningEvent findEarningEventByCommentId(String commentId);
    EarningEvent findEarningEventByShareId(String shareId);
    @Query("""
    SELECT e
    FROM EarningEvent e
    WHERE e.reviewer.id = :reviewerId
      AND e.createdAt >= :start
      AND e.createdAt < :end
      AND e.isDeleted = false
""")
    List<EarningEvent> findMonthlyEvents(
            @Param("reviewerId") String reviewerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


}
