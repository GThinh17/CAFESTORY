package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.enums.EarningSummaryStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarningSummaryResponse {
    private String id;

    private String reviewerId;

    private String userId;

    private String userName;

    private Integer year;

    private Integer month; // 1–12

    private Long totalLikesCount;

    private Long totalCommentsCount;

    private Long totalSharesCount;

    private Long totalFollowerCount;

    private String followRuleId;

    private String followRuleMin;

    private String followRuleMax;

    private BigDecimal bonusAmount;

    private BigDecimal totalEarningAmount;

    private EarningSummaryStatus status;

    private LocalDateTime createdAt;
}
