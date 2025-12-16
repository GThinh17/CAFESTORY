package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.gt.__back_end_javaspring.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByEmail(String email);

    String id(String id);

    @Query("""
                SELECT u FROM User u
                WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<User> searchUser(@Param("keyword") String keyword);
}
