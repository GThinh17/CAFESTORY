package vn.gt.__back_end_javaspring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Role;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.UserRole;
import vn.gt.__back_end_javaspring.entity.Embedded.UserRoleId;
import vn.gt.__back_end_javaspring.enums.RoleType;
import vn.gt.__back_end_javaspring.mapper.UserMapper;
import vn.gt.__back_end_javaspring.DTO.SignupDTO;
import vn.gt.__back_end_javaspring.DTO.UserDTO;
import vn.gt.__back_end_javaspring.DTO.UserResponseDTO;
import vn.gt.__back_end_javaspring.repository.RoleRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.repository.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final RoleRepository roleRepository;

	public List<UserResponseDTO> GetAllUsersDTO() {
		return this.userRepository.findAll()
				.stream()
				.map(user -> new UserResponseDTO(
						user.getAvatar(),
						user.getFullName(),
						user.getEmail(),
						user.getId(),
						user.getAddress(),
						user.getFollowerCount(),
						user.getVertifiedBank()))
				.toList();
	}

	public User createUser(User newUser) {
		return userRepository.save(newUser);
	}

	public Optional<User> getUserById(String id) {
		return this.userRepository.findById(id);
	}

	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	public void deleteUserById(String id) {
		this.userRepository.deleteById(id);
	}

	public User handleGetUserByEmail(String username) {
		return this.userRepository.findByEmail(username);
	}

	public User updateUserById(String id, UserDTO dto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Cập nhật partial bằng mapper
		userMapper.partialUpdate(dto, user);

		return userRepository.save(user);
	}

	public UserRole handleUpdateRoleUser(String userId) {

		// 1. Lấy User
		User user = this.userRepository.findByEmail(userId);

		// 2. Lấy Role USER
		Role role = this.roleRepository.findByroleName(RoleType.USER);
		if (role == null) {
			throw new RuntimeException("Role USER not found in database");
		}

		// 3. Tạo composite key
		UserRoleId id = new UserRoleId(userId, role.getId());

		// 4. Tạo entity UserRole
		UserRole userRole = new UserRole();
		userRole.setId(id);
		userRole.setRole(role);
		userRole.setUser(user);
		userRole.setCreatedAt(LocalDateTime.now());

		// 5. Lưu
		return this.userRoleRepository.save(userRole);
	}

	public User handleSignup(SignupDTO signupUser) {
		User newUser = new User();
		String email = signupUser.getEmail();
		String password = signupUser.getPassword();
		String fullname = signupUser.getFullname();
		String phone = signupUser.getPhone();
		String location = signupUser.getLocation();
		newUser.setLocation(location);
		newUser.setEmail(email);
		newUser.setFullName(fullname);
		newUser.setPassword(password);
		newUser.setPhone(phone);
		return this.userRepository.save(newUser);
	}

	public List<RoleType> getUserRole(String userId) {

		List<UserRole> userRoles = userRoleRepository.findByUser_Id(userId);

		List<RoleType> list = new ArrayList<>();

		for (UserRole ur : userRoles) {
			list.add(ur.getRole().getRoleName());
		}

		return list;
	}

	public List<UserResponseDTO> searchUser(String keyword) {

		String keyString = keyword
				.trim()
				.replaceAll("\\s+", " ")
				.toLowerCase();

		List<User> users = userRepository.searchUser(keyString);

		System.out.println("Search size = " + users.size());

		return users.stream()
				.map(user -> new UserResponseDTO(
						user.getAvatar(),
						user.getFullName(),
						user.getEmail(),
						user.getId(),
						user.getAddress(),
						user.getFollowerCount(),
						user.getVertifiedBank()))
				.toList();
	}

	// public User updateUserByUser(User user) {
	// return this.userRepository.save(user);
	// }
}
