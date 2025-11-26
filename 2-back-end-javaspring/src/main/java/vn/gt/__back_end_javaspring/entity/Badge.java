package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.CriteriaType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "badge")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "badge_id")
    private String id;

    @Column(name = "badge_name", nullable = false, length = 100)
    private String badgeName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "icon_url", length = 255)
    private String iconUrl;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "criteria_type", length = 30, nullable = false)
    private CriteriaType criteriaType;

    @Column(name = "criteria_value")
    private Long criteriaValue;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "badge", fetch = FetchType.LAZY)
    private List<ReviewerBadge> reviewerBadges = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
