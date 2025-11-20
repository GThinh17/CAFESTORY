package vn.gt.__back_end_javaspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import jakarta.persistence.Embedded;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.UserRole;
import vn.gt.__back_end_javaspring.entity.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser_Id(String userId);
}
