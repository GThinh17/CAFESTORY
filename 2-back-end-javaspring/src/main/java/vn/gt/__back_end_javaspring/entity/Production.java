package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.gt.__back_end_javaspring.enums.ProductionType;

import java.time.LocalDateTime;

@Entity
@Table(name = "production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "productionId")
    private String productionId;

    @Column(name = "description")
    private String description;

    @Column(name = "total")
    private Long total;

    @Column(name = "productionType")
    private ProductionType productionType;

    @Column(name = "timeExpired")
    private int timeExpired;

    @Column(name = "status", length = 50)
    private String status; // ACTIVE / EXPIRED / CANCELED ...

     @Column(name = "productionName", length = 50)
    private String productionName; // ACTIVE / EXPIRED / CANCELED ...
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
    }
}
