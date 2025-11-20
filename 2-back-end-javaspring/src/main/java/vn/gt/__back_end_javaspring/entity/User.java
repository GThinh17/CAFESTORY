package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User { //Check
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "user_name", nullable = false, length = 100)
	private String userName;

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
	String userFollower;

	@Column(name = "userLike")
	String userLike;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return userName;
	}

	public void setName(String name) {
		this.userName = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserFollower() {
		return userFollower;
	}

	public void setUserFollower(String userFollower) {
		this.userFollower = userFollower;
	}

	public String getUserLike() {
		return userLike;
	}

	public void setUserLike(String userLike) {
		this.userLike = userLike;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
