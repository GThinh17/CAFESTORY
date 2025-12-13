package vn.gt.__back_end_javaspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;

public interface  ReviewerRepository extends JpaRepository<Reviewer, String> {

    Boolean findByUser(User user);
    boolean existsByUser_Id(String userId);
    Reviewer findByUser_Id(String userId);

    List<Reviewer> findAllOrderByFollowerCountDesc();
}
