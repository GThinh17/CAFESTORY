package vn.gt.__back_end_javaspring.service.impl;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import vn.gt.__back_end_javaspring.service.UserService;
import vn.gt.__back_end_javaspring.enums.RoleType;

@Component("userDetailsService")
public class UserDetailCustom implements UserDetailsService {
	private final UserService userService;

	public UserDetailCustom(UserService userService) {
		this.userService = userService;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		vn.gt.__back_end_javaspring.entity.User user = this.userService.handleGetUserByEmail(username);

		List<RoleType> role = this.userService.getUserRole(user.getId());
		String[] roleNames = role.stream()
				.map(Enum::name) // chuyển USER → "USER"
				.toArray(String[]::new);

		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.roles(roleNames)
				.build();

	}

}
