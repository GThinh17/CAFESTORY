package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "earning_summary")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EarningSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "earning_summary_id")
    private String id;

    @Column(name = "total_like_count")
    private Long totalLikeCount;

    @Column(name = "total_share_count")
    private Long totalShareCount;

    @Column(name = "total_comment_count")
    private Long totalCommentCount;

    @Column(name = "total_score")
    private Long totalScore;

    @Column(name = "total_revenue_amount")
    private Long totalRevenueAmount; //So tien kiem duoc

    @Column(name = "amount")
    private Long amount; //So tien nhan duoc

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
