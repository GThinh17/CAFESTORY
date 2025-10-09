package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follower",
uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Follower {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;

    //Nguoi theo doi
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "follower_id", referencedColumnName = "id", nullable = false)
	private User follower;

    //Nguoi duoc theo doi
    //Delay fetch this column
    @ManyToOne(fetch =  FetchType.LAZY, optional = false)
    @JoinColumn(name = "following_id", referencedColumnName = "id")
    private User following;

	@Column(name = "created_at")
    LocalDateTime createAt;

    @PrePersist
    protected void onCreate(){
        createAt = LocalDateTime.now();
    }

}