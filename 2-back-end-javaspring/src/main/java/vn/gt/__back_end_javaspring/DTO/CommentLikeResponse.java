package vn.gt.__back_end_javaspring.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentLikeResponse {
    String id;
    String userId;
    String userFullName;
    String userAvatar;
    String commentId;
    LocalDateTime createdAt;
}
