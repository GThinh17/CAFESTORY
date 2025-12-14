package vn.gt.__back_end_javaspring.DTO;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import vn.gt.__back_end_javaspring.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CafeOwnerResponse {
    private String id;

    private String userId;

    private String userName;

    private String userAvatar;

    private String businessName;

    private Integer totalReview;

    private BigDecimal averageRating;

    private String contactEmail;

    private String contactPhone;

    private LocalDateTime expiredAt;

    private LocalDateTime joinAt;

    private LocalDateTime createdAt;
}
