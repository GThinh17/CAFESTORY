package vn.gt.__back_end_javaspring.service;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;

import java.util.List;

@Service
public interface NotificationService {
    void sendNotification(String receiverId, NotificationRequestDTO notificationRequestDTO);
    NotificationResponse createNotification(NotificationRequestDTO notificationRequestDTO);
    List<NotificationResponse> getNoticationByUserId(String userId );
    void deleteNotification(String notificationId);
    void markUnread(String notificationId);
    void markRead(String notificationId);
    void markAllRead(String userId );
}
