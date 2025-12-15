package vn.gt.__back_end_javaspring.DTO;
import jakarta.persistence.Column;
import lombok.*;
import vn.gt.__back_end_javaspring.entity.Reviewer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarningSummaryCreateDTO {
    private String reviewerId;

    private Integer year;

    private Integer month;

    private Long totalLikesCount;

    private Long totalCommentsCount;

    private Long totalSharesCount;

    private Long totalFollowersCount;

    private BigDecimal totalEarningAmount;
}
