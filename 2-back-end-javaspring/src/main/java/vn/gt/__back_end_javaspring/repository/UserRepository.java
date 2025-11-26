package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	User findByEmail(String email);

}
