package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;

public interface  ReviewerRepository extends JpaRepository<Reviewer, String> {

    Boolean findByUser(User user);
    boolean existsByUser_Id(String userId);
}
