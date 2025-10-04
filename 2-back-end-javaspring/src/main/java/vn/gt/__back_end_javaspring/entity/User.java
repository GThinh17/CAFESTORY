package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
	@Id
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;

	@Column(name = "phone", unique = true, nullable = false, length = 15)
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
	String userFollower;

	@Column(name = "userLike")
	String userLike;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
