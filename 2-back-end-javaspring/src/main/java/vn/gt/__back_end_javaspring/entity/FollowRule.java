package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "follow_rule")
public class FollowRule { //Tao chay
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "follow_rule_id")
    private String id;


    @Column(name = "min_follow")
    private Long minFollow;

    @Column(name = "max_follow")
    private Long maxFollow;

    @Column(name = "bonus_amount")
    private BigDecimal bonusAmount;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
