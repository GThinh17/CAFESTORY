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
public class Comment {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "comment_id")
        private String id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "blog_id")
        private Blog blog;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_commend_id")
        private Comment commentParent;

        @OneToMany(mappedBy = "commentParent",  cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Comment> replies = new ArrayList<>();

        @Column(name = "content", nullable = false, length = 500)
        private String content;

        @Column(name = "likes_count")
        private Long likesCount;

        @Column(name = "reply_count")
        private Long replyCount;

        @Column(name = "is_edited")
        private Boolean isEdited;

        @Column(name = "is_deleted")
        private Boolean isDeleted;

        @Column(name = "cooldown_second")
        private Long cooldownSecond;

        @Column(name = "is_pin")
        private Boolean isPin;

        @Column(name = "created_at")
        private LocalDateTime createdAt;



}
