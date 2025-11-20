package vn.gt.__back_end_javaspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.DTO.LoginDTO;
import vn.gt.__back_end_javaspring.DTO.RestLoginDTO;
import vn.gt.__back_end_javaspring.DTO.SignupDTO;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.UserService;
import vn.gt.__back_end_javaspring.util.SecurityUtil;

@RestController
public class AuthController {

	private final UserRepository userRepository;

	private final UserService userService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final SecurityUtil securityUtil;
	private final PasswordEncoder passwordEncoder;

	public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
			UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.securityUtil = securityUtil;
		this.userService = userService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("api/login")
	public ResponseEntity<RestLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {

		// Nạp input gồm username/password vào Security
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDTO.getUsername(), loginDTO.getPassword());

		// xác thực người dùng => cần viết hàm loadUserByUsername
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        System.out.println(authentication);
		// create token
		String access_token = securityUtil.createToken(authentication);
		RestLoginDTO restLoginDTO = new RestLoginDTO(access_token);

		// nạp thông tin (nếu xử lý thành công) vào SecurityContext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return ResponseEntity.ok().body(restLoginDTO);
	}

	@PostMapping("api/signup")
	public ResponseEntity<User> signup(@Valid @RequestBody SignupDTO signUp) {
		User newUser = new User();

		newUser.setEmail(signUp.getEmail());
		newUser.setFullName(signUp.getFullname());
		newUser.setPhone(signUp.getPhone());
		newUser.setName(signUp.getName());

		// hashpassword
		String hashPassword = this.passwordEncoder.encode(signUp.getPassword());
		signUp.setPassword(hashPassword);
		this.userService.handleSignup(signUp);

		return ResponseEntity.ok().body(newUser);
	}
}
