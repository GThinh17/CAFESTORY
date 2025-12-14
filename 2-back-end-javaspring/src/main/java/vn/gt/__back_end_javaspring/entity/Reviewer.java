package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import vn.gt.__back_end_javaspring.enums.ReviewerStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "reviewer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reviewer_id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "follower_count")
    Integer followerCount;

    @Column(name = "following_count")
    Integer followingCount;


    @Column(name = "join_at")
    private LocalDateTime joinAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReviewerStatus status;

    @PrePersist
    public void prePersist() {
        if (joinAt == null) {
            joinAt = LocalDateTime.now();
        }
        if (status == null) {
            status = ReviewerStatus.PENDING;
        }
        if (totalScore == null) {
            totalScore = 0;
        }
        this.isDeleted = false;
    }
}
