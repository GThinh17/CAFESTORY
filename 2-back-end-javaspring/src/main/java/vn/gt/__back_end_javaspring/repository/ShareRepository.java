package vn.gt.__back_end_javaspring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.gt.__back_end_javaspring.entity.Share;

public interface ShareRepository extends JpaRepository<Share, String> {

    @Query("""
           select s from Share s
           join s.shareDetails d
           where d.blog.id = :blogId
           order by s.createdAt desc
           """)
    List<Share> findAllByBlogIdOrderByCreatedAtDesc(String blogId);

    @Query("""
           select s from Share s
           join s.shareDetails d
           where d.user.id = :userId
           order by s.createdAt desc
           """)
    List<Share> findAllByUserIdOrderByCreatedAtDesc(String userId);

    @Query("""
           select s from Share s
           join s.shareDetails d
           where d.user.id = :userId and d.blog.id = :blogId
           """)
    Optional<Share> findByUserIdAndBlogId(String userId, String blogId);

    @Query("""
           select count(s) from Share s
           join s.shareDetails d
           where d.blog.id = :blogId
           """)
    long countByBlogId(String blogId);
}
