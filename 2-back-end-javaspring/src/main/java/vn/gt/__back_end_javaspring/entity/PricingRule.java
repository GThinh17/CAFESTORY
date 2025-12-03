package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pricing_rule")
@Data
public class PricingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pricing_rule_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "like_weight")
    private BigDecimal likeWeight; //Trong so cua like 1 like = 2 diem

    @Column(name = "comment_weight")
    private BigDecimal commentWeight;

    @Column(name = "share_weight")
    private BigDecimal shareWeight;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice; //Gia tien tren moi diem

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        isActive = true;
        currency = "USD";
        isDeleted = false;
    }
}
