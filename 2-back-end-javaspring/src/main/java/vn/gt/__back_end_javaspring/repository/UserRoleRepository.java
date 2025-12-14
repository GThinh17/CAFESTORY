package vn.gt.__back_end_javaspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;

import vn.gt.__back_end_javaspring.DTO.UserRoleResponse;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.UserRole;
import vn.gt.__back_end_javaspring.entity.Embedded.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser_Id(String userId);

    UserRole findByUser_IdAndRole_Id(String userId, String roleId);

    boolean existsByUser(User user);

}
