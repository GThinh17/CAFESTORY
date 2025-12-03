package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer, String> {

}
