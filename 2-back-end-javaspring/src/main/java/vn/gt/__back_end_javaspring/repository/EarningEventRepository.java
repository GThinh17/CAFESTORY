package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.EarningEvent;

public interface EarningEventRepository extends JpaRepository<EarningEvent, String> {
    EarningEvent findEarningEventByLikeId(String likeId);
    EarningEvent findEarningEventByCommentId(String commentId);
    EarningEvent findEarningEventByShareId(String shareId);
}
