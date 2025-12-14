package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.FollowType;

import java.time.LocalDateTime;
@Entity
@Table(
        name = "follow",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_follower_target",
                        columnNames = {"follower_id", "follow_type", "followed_user_id", "followed_page_id"}
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "follow_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower; //thang follow

    @Enumerated(EnumType.STRING)
    @Column(name = "follow_type", nullable = false)
    private FollowType followType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_user_id")
    private User followedUser; //Thang duoc follow

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_reviewer_id")
    private Reviewer followedReviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_page_id")
    private Page followedPage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
