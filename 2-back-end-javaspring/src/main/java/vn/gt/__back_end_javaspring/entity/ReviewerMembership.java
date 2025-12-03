package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewerMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "productionId")
    private String id;

    @Column(name = "productionName")
    private String productionName;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "status", length = 50)
    private String status; // ACTIVE / EXPIRED / CANCELED ...

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
