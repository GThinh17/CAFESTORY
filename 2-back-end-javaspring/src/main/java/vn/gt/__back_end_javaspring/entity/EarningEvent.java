package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.gt.__back_end_javaspring.enums.SourceType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "earning_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarningEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Reviewer reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricing_rule_id", nullable = false)
    private PricingRule pricingRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "like_id")
    private String likeId;

    @Column(name = "share_id")
    private String shareId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", length = 50)
    private SourceType sourceType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        isDeleted = false;
        createdAt = LocalDateTime.now();
    }
}
