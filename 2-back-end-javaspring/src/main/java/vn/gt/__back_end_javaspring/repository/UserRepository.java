package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByemail(String email);
}
