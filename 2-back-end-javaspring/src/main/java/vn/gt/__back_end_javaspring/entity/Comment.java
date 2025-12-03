package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {//Check
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "comment_id")
        private String id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "blog_id")
        private Blog blog;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_comment_id")
        private Comment parentComment;

        @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
        private List<Comment> replies = new ArrayList<>();

        @Column(name = "content", nullable = false, length = 500)
        private String content;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "comment_image_id")
        private CommentImage commentImage;

        @Column(name = "likes_count")
        private Long likesCount;

        @Column(name = "reply_count")
        private Long replyCount;

        @Column(name = "is_edited")
        private Boolean isEdited;

        @Column(name = "is_deleted")
        private Boolean isDeleted; //Khong xoa dc deleted

        @Column(name = "deleted_at")
        private LocalDateTime deletedAt;

        @Column(name = "is_pin")
        private Boolean isPin;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;



        @PrePersist
        public void onCreate() {
            if (this.createdAt == null) this.createdAt = LocalDateTime.now();
            if (this.likesCount == null) this.likesCount = 0L;
            if (this.replyCount == null) this.replyCount = 0L;
            if (this.isDeleted == null) this.isDeleted = Boolean.FALSE;
            if (this.isEdited == null) this.isEdited = Boolean.FALSE;
            if (this.isPin == null) this.isPin = Boolean.FALSE;
        }


         @PreUpdate
        public void onUpdate() {
            this.updatedAt = LocalDateTime.now();
        }
}
