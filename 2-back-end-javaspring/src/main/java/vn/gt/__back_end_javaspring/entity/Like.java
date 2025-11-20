package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog_like"
, indexes = {
        @Index(
                name = "idx_blog_like_user_blog",
                columnList = "user_id, blog_id"
        ), //tao index
        @Index(
                name = "idx_blog_like_blog",
                columnList = "blog_id"
        )
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like { //cHECK
	@Id
	@Column(name = "blog_like_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blog_id")
    private Blog blog;
    
	@Column(name = "created_at")
	private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}