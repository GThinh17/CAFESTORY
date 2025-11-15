package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import vn.gt.__back_end_javaspring.enums.Visibility;

@Entity
@Table(name = "blog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
        @Id
        @Column(name = "blog_id")
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(name = "caption")
        private String caption;

        @Column(name = "likes_count")
        private Long likesCount;

        @Column(name = "shares_count")
        private Long sharesCount;

        @Column(name = "comments_count")
        private Long commentsCount;

        @Column(name = "is_pin")
        private Boolean isPin;

        @Column(name = "moderation_status")
        private String moderationStatus;

        @Column(name = "moderation_reason")
        private String moderationReason;

        @Column(name = "allow_comment")
        private Boolean allowComment;

        @Enumerated(EnumType.STRING)
        @Column(name = "visibility")
        private Visibility visibility;

        @Column(name = "cooldown_second")
        private Long cooldownSecond;

        @Column(name = "is_deleted")
        private Boolean isDeleted;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Media> mediaList = new ArrayList<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "location_id")
        private Location location;

}
