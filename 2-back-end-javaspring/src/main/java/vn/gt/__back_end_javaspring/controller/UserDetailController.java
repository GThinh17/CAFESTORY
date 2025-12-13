package vn.gt.__back_end_javaspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.service.UserService;

@RestController
@RequestMapping("users")
public class UserDetailController {
	private final UserService userService;

	public UserDetailController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/getme")
	public ResponseEntity<User> getUserInformation(Authentication authentication) {
		String email = authentication.getName();
		User user = this.userService.handleGetUserByEmail(email);
		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/updateme")
	public ResponseEntity<User> updateUserInformation(Authentication authentication, User updateUser) {
		String email = authentication.getName();
		User user = this.userService.handleGetUserByEmail(email);
		user = updateUser;
		this.userService.createUser(user);
		return ResponseEntity.ok().body(user);
	}

}
