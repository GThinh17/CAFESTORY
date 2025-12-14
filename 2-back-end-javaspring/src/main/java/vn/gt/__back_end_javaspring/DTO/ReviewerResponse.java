package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewerResponse {
    private String id;
    private String userId;
    private String userName;
    private String userAvatarUrl;
    private String userEmail;
    private String bio;

    private Integer followerCount;

    private Integer totalScore;

    private LocalDateTime joinAt;

    private LocalDateTime expiredAt;
    private Boolean isDeleted;
    private String status;
}

