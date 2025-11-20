package vn.gt.__back_end_javaspring.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.gt.__back_end_javaspring.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, String> {

    boolean existsByUser_IdAndBlog_id(String userId, String blogId);

    Optional<Like> findByUser_IdAndBlog_Id(String userId, String blogId);

    long countByBlog_Id(String blogId);

    List<Like> findByBlog_Id(String blogId);

    @Modifying
    @Transactional
    @Query("delete from Like l where l.user.id = :userId and l.blog.id = :blogId")
    int deleteByUserAndBlog(String userId, String blogId);
}

//
//public interface LikeRepository extends JpaRepository<Like, String> {
//
//    @Query(
//            value = "SELECT EXISTS(SELECT 1 FROM blog_like WHERE user_id = :userId AND blog_id = :blogId)",
//            nativeQuery = true
//    )
//    boolean exists(@Param("userId") String userId,
//                   @Param("blogId") String blogId);
//
//    @Query(
//            value = "SELECT * FROM blog_like WHERE user_id = :userId AND blog_id = :blogId LIMIT 1",
//            nativeQuery = true
//    )
//    Optional<Like> find(@Param("userId") String userId,
//                        @Param("blogId") String blogId);
//
//    @Query(
//            value = "SELECT COUNT(*) FROM blog_like WHERE blog_id = :blogId",
//            nativeQuery = true
//    )
//    long countLike(@Param("blogId") String blogId);
//
//    @Query(
//            value = "SELECT * FROM blog_like WHERE blog_id = :blogId",
//            nativeQuery = true
//    )
//    List<Like> getLikes(@Param("blogId") String blogId);
//}
