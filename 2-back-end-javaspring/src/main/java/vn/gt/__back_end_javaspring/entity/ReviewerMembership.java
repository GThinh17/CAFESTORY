package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviewer_membership")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewerMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reviewer_membership_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private Reviewer reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricing_plan_id", nullable = false)
    private PricingPlan pricingPlan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", unique = true, nullable = false)
    private Payment payment;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "status", length = 50)
    private String status; // ACTIVE / EXPIRED / CANCELED ...

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
