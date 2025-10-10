package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.gt.__back_end_javaspring.entity.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {
    List<Blog> findBlogByUserId(String userId);
    List<Blog> findNewestBlogByCreatedAtDesc();
}

