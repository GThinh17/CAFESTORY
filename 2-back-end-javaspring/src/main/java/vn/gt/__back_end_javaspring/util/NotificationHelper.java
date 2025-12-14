package vn.gt.__back_end_javaspring.util;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;
import vn.gt.__back_end_javaspring.enums.NotificationType;

public class NotificationHelper {

    private NotificationHelper() {}

    public static NotificationRequestDTO build(
            String senderId,
            String receiverId,
            NotificationType type,
            String postId,
            String commentId,
            String pageId,
            String walletTransactionId,
            String badgeId,
            String content
    ) {
        return NotificationRequestDTO.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .type(type)
                .postId(postId)
                .commentId(commentId)
                .pageId(pageId)
                .walletTransactionId(walletTransactionId)
                .badgeId(badgeId)
                .content(content)
                .build();
    }
}
