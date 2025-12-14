package vn.gt.__back_end_javaspring.DTO;
import lombok.*;
import vn.gt.__back_end_javaspring.enums.NotificationType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {
    private String senderId;
    private String receiverId;
    private NotificationType type;
    private String postId;
    private String commentId;
    private String pageId;
    private String walletTransactionId;
    private String badgeId;
    private String content;
}
