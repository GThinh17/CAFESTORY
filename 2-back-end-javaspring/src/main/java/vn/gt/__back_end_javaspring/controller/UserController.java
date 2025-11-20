package vn.gt.__back_end_javaspring.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
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

	public static boolean hasRole(Jwt jwt, String role) {
		List<Map<String, Object>> roles = jwt.getClaim("roles");
		if (roles == null)
			return false;

		return roles.stream()
				.anyMatch(r -> role.equals(r.get("role")));
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {

		return ResponseEntity.ok().body(this.userService.getAllUsers());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") String id) {

		User user = this.userService.getUserById(id);
		return ResponseEntity.ok().body(user);

	}

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody User newUser, @AuthenticationPrincipal Jwt jwt) {
		// hash Password by Password Encoder
		if (!hasRole(jwt, "ROLE_ADMIN")) {
			return ResponseEntity.status(403).body("Access denied: You are not ADMIN");
		}
		String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
		newUser.setPassword(hashPassword);

		User user = this.userService.createUser(newUser);

		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User updateUser, @PathVariable("id") String id,
			@AuthenticationPrincipal Jwt jwt) {
		if (!hasRole(jwt, "ROLE_ADMIN")) {
			return ResponseEntity.status(403).body("Access denied: You are not ADMIN");
		}
		User user = this.userService.updateUserById(id, updateUser);
		return ResponseEntity.ok().body(user);
	}
}
