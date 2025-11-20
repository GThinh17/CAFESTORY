package vn.gt.__back_end_javaspring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.DTO.SignupDTO;
import vn.gt.__back_end_javaspring.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(User newUser) {
		return userRepository.save(newUser);
	}

	public User getUserById(String id) {
		return this.userRepository.findByid(id);
	}

	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	public void deleteUserById(String id) {
		this.userRepository.deleteById(id);
	}

	public User handleGetUserByEmail(String username) {
		return this.userRepository.findByemail(username);
	}

	public User handleSignup(SignupDTO signupUser) {
		User newUser = new User();
		String email = signupUser.getEmail();
		String password = signupUser.getPassword();
		String fullname = signupUser.getFullname();
		String phone = signupUser.getPhone();
		String name = signupUser.getName();
		newUser.setEmail(email);
		newUser.setFullName(name);
		newUser.setFullName(fullname);
		newUser.setPassword(password);
		newUser.setPhone(phone);
		return this.userRepository.save(newUser);
	}

}
