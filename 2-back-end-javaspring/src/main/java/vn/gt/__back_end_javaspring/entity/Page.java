package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "page")
@Data
@NoArgsConstructor
@AllArgsConstructor // Check
public class Page {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "page_id")
        private String id;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private CafeOwner cafeOwner;

        @NotBlank
        @Column(name = "page_name", nullable = false, length = 150)
        private String pageName;

        @Column(name = "slug")
        private String slug;

        @Column(name = "post_count", nullable = false)
        private Long postCount = 0L;

        @Column(name = "following_count") // Bo truogn nay
        private Long followingCount;

        @Column(name = "description", length = 1000)
        private String description;

        @Column(name = "avatar_url", length = 500)
        private String avatarUrl;

        @Column(name = "cover_url", length = 500)
        private String coverUrl;

        @Column(name = "contact_phone", length = 30)
        private String contactPhone;

        @Email
        @Column(name = "contact_email", length = 255)
        private String contactEmail;

        @Column(name = "is_verified", nullable = false)
        private Boolean isVerified = false;

        @Column(name = "verified_at")
        private LocalDateTime verifiedAt;

        @Column(name = "open_hours", columnDefinition = "json")
        private String openHours;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @Column(name = "is_deleted")
        private Boolean isDeleted;

        @PrePersist
        private void prePersist() {
                createdAt = LocalDateTime.now();
                updatedAt = LocalDateTime.now();
                this.postCount = 0L;
                this.followingCount = 0L;
                this.isDeleted = false;

        }

        @PreUpdate
        private void preUpdate() {
                updatedAt = LocalDateTime.now();
        }
}
