package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.gt.__back_end_javaspring.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id")
    private String paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_id")
    private Production production;

    @Column(nullable = false)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private PaymentStatus status; // PENDING / SUCCESS / FAILED ...

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private Payout payout;

    @PrePersist
    public void prePersist() {
        processedAt = LocalDateTime.now();
        status = PaymentStatus.PENDING;
    }
}
