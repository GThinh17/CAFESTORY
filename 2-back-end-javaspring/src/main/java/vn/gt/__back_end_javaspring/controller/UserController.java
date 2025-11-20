package vn.gt.__back_end_javaspring.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.service.UserService;

@RestController
public class UserController {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserController(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(this.userService.getAllUsers());

	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		User user = this.userService.getUserById(id);
		return ResponseEntity.ok().body(user);

	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User newUser) {
		// hash Password by Password Encoder
		String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
		newUser.setPassword(hashPassword);

		User user = this.userService.createUser(newUser);

		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User updateUser, @PathVariable("id") String id) {
		User user = userService.getUserById(id);
		user.setAddress(updateUser.getAddress());
		user.setDateOfBirth(updateUser.getDateOfBirth());
		user.setFullName(updateUser.getFullName());
		user.setFullName(updateUser.getFullName());
		user.setUpdatedAt(LocalDateTime.now());
		user = this.userService.createUser(user);
		return ResponseEntity.ok().body(user);
	}
}
