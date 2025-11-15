package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.ShareDetail;
import vn.gt.__back_end_javaspring.entity.ShareDetailId;

public interface ShareDetailRepository extends JpaRepository<ShareDetail, ShareDetailId> {
    boolean existsByUser_IdAndBlog_Id(String userId, String blogId);
}
