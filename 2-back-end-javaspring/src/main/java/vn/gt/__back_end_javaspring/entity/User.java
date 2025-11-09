package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
>>>>>>> 23145a3cc3b309ad220d0324b3ee59e613a97687

@Table(name = "user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
=======
@Data
>>>>>>> 23145a3cc3b309ad220d0324b3ee59e613a97687
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

<<<<<<< HEAD
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
=======
    //mappedBy anh xa tu truong ben share qua
    //List all blog user share
    @JsonIgnore //Non-reply on Json
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Share> shares;

    @JsonIgnore
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    private List<Follower> followers;
>>>>>>> 23145a3cc3b309ad220d0324b3ee59e613a97687

    @JsonIgnore
    @OneToMany(mappedBy = "following",cascade = CascadeType.ALL)
    private List<Follower> followings;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
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
