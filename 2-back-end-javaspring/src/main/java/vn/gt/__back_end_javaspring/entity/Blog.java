package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Blog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "content", nullable = false,  length = 1000,updatable = true)
	private String content;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "likes_count", updatable = true)
	private int likeCount;

	@Column(name = "shares_count")
	private int shareCount;

	@Column(name = "status",  nullable = false,  updatable = true)
	private boolean status;

	@Column(name = "created_at")
	private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //No se noi la Blog khong so huu khoa ngoai, khoa ngoai nam o ben Commet va trong blog
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments;


    @OneToMany(mappedBy = "blog")
    private List<Share> shares;


    @PrePersist
    protected void onCreate()
    {
        createAt = LocalDateTime.now();
    }

}