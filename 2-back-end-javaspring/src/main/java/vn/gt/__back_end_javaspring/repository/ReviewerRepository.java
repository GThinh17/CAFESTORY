package vn.gt.__back_end_javaspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.Report;
import vn.gt.__back_end_javaspring.entity.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer, String> {

}
