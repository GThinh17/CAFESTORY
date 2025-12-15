package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import vn.gt.__back_end_javaspring.enums.PlatformType;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_device")
public class UserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fcm_token", unique = true, nullable = false)
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform")
    private PlatformType platform;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
