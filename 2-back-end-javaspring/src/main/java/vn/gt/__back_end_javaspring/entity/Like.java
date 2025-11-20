package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like { // cHECK
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Column(name = "create_at")
    private LocalDateTime createAt;

}