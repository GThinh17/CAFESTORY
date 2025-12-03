package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.Share;

import java.util.List;
import java.util.Optional;

public interface ShareRepository extends JpaRepository<Share, String> {
    Optional<Share> findByIdAndIsDeletedFalse(String id);

    List<Share> findByBlog_IdAndIsDeletedFalseOrderByCreatedAtDesc(String blogId);

    List<Share> findByUser_IdAndIsDeletedFalseOrderByCreatedAtDesc(String userId);
}
