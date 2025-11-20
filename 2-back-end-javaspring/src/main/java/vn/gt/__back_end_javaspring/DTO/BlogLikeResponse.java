package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogLikeResponse {
    String id;
    String userId;
    String userFullName;
    String userAvatar;
    String blogId;
    LocalDateTime createdAt;
}
