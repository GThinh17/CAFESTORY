package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cafe_owner")
public class CafeOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cafe_owner_id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "total_review")
    private Integer totalReview;

    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "follower_count")
    private Long followerCount;

    @Column(name = "join_at")
    private LocalDateTime joinAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        averageRating = BigDecimal.ZERO;
        totalReview = 0;
        joinAt = LocalDateTime.now();
        isDeleted = false;
    }

}
