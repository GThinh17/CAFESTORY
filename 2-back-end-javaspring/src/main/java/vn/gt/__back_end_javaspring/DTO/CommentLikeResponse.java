package vn.gt.__back_end_javaspring.DTO;

<<<<<<< HEAD
import lombok.Builder;
import lombok.Data;
=======
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> origin/develop

import java.time.LocalDateTime;

@Data
<<<<<<< HEAD
=======
@AllArgsConstructor
@NoArgsConstructor
>>>>>>> origin/develop
@Builder
public class CommentLikeResponse {
    String id;
    String userId;
    String userFullName;
    String userAvatar;
    String commentId;
    LocalDateTime createdAt;
}
