package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follower")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PageFollow {
	@Id
	@Column(name = "follow_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "page_id")
    private Page page;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

	@Column(name = "created_at")
    LocalDateTime createdAt;

}