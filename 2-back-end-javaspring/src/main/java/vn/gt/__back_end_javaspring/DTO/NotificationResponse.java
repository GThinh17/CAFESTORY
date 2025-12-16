package vn.gt.__back_end_javaspring.DTO;
import jakarta.persistence.*;
import lombok.*;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private String id;

    private String receiverId;

    private String senderId;

    private String senderAvatar;

    private String senderName;

    private NotificationType type;

    private String title;

    private String body;

    private Boolean read;

    private LocalDateTime createdAt;
}
