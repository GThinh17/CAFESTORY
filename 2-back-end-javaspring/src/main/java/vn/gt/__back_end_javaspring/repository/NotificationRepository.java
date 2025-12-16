package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.*;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    Page<Notification> findByReceiver_IdAndReadIsFalseOrderByCreatedAtDesc(String receiverId, Pageable pageable);
    Page<Notification> findByReceiver_IdOrderByCreatedAtDesc(String receiverId, Pageable pageable);
    List<Notification> findByReceiver_IdAndReadIsFalse(String receiverId);
    Long countByReceiver_IdAndReadIsFalse(String receiverId);
    List<Notification> findByReceiver_IdOrderByCreatedAtDesc(String receiverId);
}
