package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.Blog;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, String> {
    Optional<Blog> findBlogById(String blogId);
}
