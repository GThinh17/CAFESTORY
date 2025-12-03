package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import vn.gt.__back_end_javaspring.enums.TransactionStatus;
import vn.gt.__back_end_javaspring.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "wallet_transaction")
public class WalletTransaction {
    @Id
    @Column(name = "wallet_transaction_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "amount", precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TransactionType type; //EARNING//PAYOUT/ ADJUSTMENT

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;


    @Column(name = "balance_after", precision = 18, scale = 2, nullable = false)
    private BigDecimal balanceAfter;

    @Column(name = "balance_before",  precision = 18, scale = 2, nullable = false)
    private BigDecimal balanceBefore;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void onPrePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = TransactionStatus.PENDING;
        isDeleted = Boolean.FALSE;
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
