package vn.gt.__back_end_javaspring.entity;

import com.google.type.DateTime;
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
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "like_weight")
    private BigDecimal likeWeight;

    @Column(name = "comment_weight")
    private BigDecimal commentWeight;

    @Column(name = "share_weight")
    private BigDecimal shareWeight;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "currency", length = 10)
    private String currency;

    @Column(name = "effective_from")
    private DateTime effectiveFrom;

    @Column(name = "effective_to")
    private DateTime effectiveTo;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        likeWeight = BigDecimal.ZERO;
        commentWeight = BigDecimal.ZERO;
        shareWeight = BigDecimal.ZERO;
        unitPrice = BigDecimal.ZERO;
        isActive = true;
    }
}
