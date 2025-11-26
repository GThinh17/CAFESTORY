package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.CommentImage;

public interface commentImageRepository extends JpaRepository<CommentImage, String> {
}
