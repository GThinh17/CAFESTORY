package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "full_name", length = 100)
	private String fullName;

	@Column(name = "phone", length = 15)
	private String phone;

	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "address", length = 255)
	private String address;

	@Column(name = "avatar", length = 255)
	private String avatar;

	@Column(name = "userFollower")
	String userFollower; //?

	@Column(name = "userLike")
	String userLike; //?

	@Column(name = "created_at")
	protected LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

    //mappedBy anh xa tu truong ben share qua
    //List all blog user share
    @JsonIgnore //Non-reply on Json
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Share> shares;

    @JsonIgnore
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follower> followers;

    @JsonIgnore
    @OneToMany(mappedBy = "following",cascade = CascadeType.ALL)
    private List<Follower> followings;

    @JsonIgnore
    @OneToMany(mappedBy = "blog")
    private List<Blog> blogs;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
