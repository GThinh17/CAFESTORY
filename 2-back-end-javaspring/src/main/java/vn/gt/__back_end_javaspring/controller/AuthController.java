package vn.gt.__back_end_javaspring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.gt.__back_end_javaspring.entity.DTO.LoginDTO;
import vn.gt.__back_end_javaspring.entity.DTO.RestLoginDTO;
import vn.gt.__back_end_javaspring.service.impl.until.SecurityUtil;

@RestController
public class AuthController {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final SecurityUtil securityUtil;

	public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.securityUtil = securityUtil;
	}

	@PostMapping("api/login")
	public ResponseEntity<RestLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
		// Nạp input gồm username/password vào Security
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDTO.getUsername(), loginDTO.getPassword());

		// xác thực người dùng => cần viết hàm loadUserByUsername
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// create token
		String access_token = securityUtil.createToken(authentication);
		RestLoginDTO restLoginDTO = new RestLoginDTO(access_token);
		// nạp thông tin (nếu xử lý thành công) vào SecurityContext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return ResponseEntity.ok().body(restLoginDTO);
	}
}
