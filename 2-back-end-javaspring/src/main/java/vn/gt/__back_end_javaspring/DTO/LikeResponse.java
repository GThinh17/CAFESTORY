package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResponse {
    String id;
    String userId;
    String userFullName;
    String userAvatar;
    String blogId;
    LocalDateTime createdAt;
}
