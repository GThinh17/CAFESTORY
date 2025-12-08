package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import vn.gt.__back_end_javaspring.enums.NotificationType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private String id;

    private NotificationType type;

    private String userId;

    private String userName;

    private String title;

    private String content;

    private String redirectUrl;

    private boolean read;

    private LocalDateTime createdAt;

    private String actorId;
    private String actorName;
    private String actorAvatarUrl;
}
