package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "earning_summary",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_reviewer_period",
                columnNames = {"reviewer_id", "period_id"}
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarningSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "summary_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Reviewer reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_id", nullable = false)
    private EarningPeriod earningPeriod;

    @Column(name = "total_likes_count")
    private Long totalLikesCount;

    @Column(name = "total_comments_count")
    private Long totalCommentsCount;

    @Column(name = "total_shares_count")
    private Long totalSharesCount;

    @Column(name = "total_earning_amount")
    private BigDecimal totalEarningAmount;

    @Column(name = "status", length = 50)
    private String status; // OPEN / CLOSED / PAID...

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
