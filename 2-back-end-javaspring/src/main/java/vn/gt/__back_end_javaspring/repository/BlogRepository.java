package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.gt.__back_end_javaspring.entity.Blog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, String> {

    @Query("""
        SELECT b FROM Blog b
        WHERE b.visibility = vn.gt.__back_end_javaspring.enums.Visibility.PUBLIC
          AND (b.isDeleted IS NULL OR b.isDeleted = false)
        ORDER BY b.createdAt DESC, b.id DESC
    """)
    List<Blog> firstPage(Pageable pageable);

    @Query("""
        SELECT b FROM Blog b
        WHERE b.visibility = vn.gt.__back_end_javaspring.enums.Visibility.PUBLIC
          AND (b.isDeleted IS NULL OR b.isDeleted = false)
          AND (b.createdAt < :lastCreatedAt
               OR (b.createdAt = :lastCreatedAt AND b.id < :lastId))
        ORDER BY b.createdAt DESC, b.id DESC
    """)
    List<Blog> nextPage(
            @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
            @Param("lastId") String lastId,
            Pageable pageable
    );


    @Query("""
        SELECT b FROM Blog b
        WHERE b.user.id = :userId
          AND (b.isDeleted IS NULL OR b.isDeleted = false)
        ORDER BY b.createdAt DESC, b.id DESC
    """)
    List<Blog> firstUserPage(
            @Param("userId") String userId,
            Pageable pageable
    );

    @Query("""
        SELECT b FROM Blog b
        WHERE b.user.id = :userId
          AND (b.isDeleted IS NULL OR b.isDeleted = false)
          AND (b.createdAt < :lastCreatedAt
               OR (b.createdAt = :lastCreatedAt AND b.id < :lastId))
        ORDER BY b.createdAt DESC, b.id DESC
    """)
    List<Blog> nextUserPage(
            @Param("userId") String userId,
            @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
            @Param("lastId") String lastId,
            Pageable pageable
    );


    // Lấy tất cả blog theo user (không phân trang), mới -> cũ
    List<Blog> findByUser_IdOrderByCreatedAtDescIdDesc(String userId);

    // Blog mới nhất toàn hệ thống
    Blog findTopByOrderByCreatedAtDesc();
}
