package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.Role;
import vn.gt.__back_end_javaspring.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, String> {
     Role findByroleName(RoleType name);
}
