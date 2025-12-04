package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.DTO.BadgeResponse;
import vn.gt.__back_end_javaspring.entity.Badge;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, String> {
    List<Badge> findAllByIsDeletedFalse();
}
