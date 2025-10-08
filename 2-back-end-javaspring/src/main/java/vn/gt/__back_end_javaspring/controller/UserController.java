package vn.gt.__back_end_javaspring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import vn.gt.__back_end_javaspring.entity.RestResponse;
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
	public RestResponse<List<User>> getAllUsers() {
		RestResponse<List<User>> rest = new RestResponse<>();
		rest.setData(this.userService.getAllUsers());
		rest.setStatusCode(200);
		rest.setMessage("Get all users successful");
		return rest;
	}

	@GetMapping("/users/{id}")
	public RestResponse<Optional<User>> getUser(@PathVariable("id") Long id) {
		RestResponse<Optional<User>> rest = new RestResponse<>();
		Optional<User> user = this.userService.getUserById(id);
		rest.setData(user);
		rest.setStatusCode(201);
		return rest;
	}

	@PostMapping("/users")
	public RestResponse<User> createUser(@RequestBody User newUser) {
		// hash Password by Password Encoder
		String hashPassword = this.passwordEncoder.encode(newUser.getPassword());
		newUser.setPassword(hashPassword);

		User user = this.userService.createUser(newUser);
		RestResponse<User> restResponse = new RestResponse<>();

		restResponse.setData(user);
		restResponse.setStatusCode(202);
		restResponse.setMessage("Create user successful");

		return restResponse;
	}
}
