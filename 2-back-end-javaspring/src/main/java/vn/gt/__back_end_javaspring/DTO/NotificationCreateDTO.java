package vn.gt.__back_end_javaspring.DTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.NotificationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationCreateDTO {
    private User receiver;

    private User actor;

    private NotificationType type;

    private String title;

    private String content;

    private String redirectUrl;
}
