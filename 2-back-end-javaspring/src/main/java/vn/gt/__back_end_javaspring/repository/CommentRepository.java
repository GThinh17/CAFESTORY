package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.gt.__back_end_javaspring.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("""
        SELECT c FROM Comment c
        WHERE c.blog.id = :blogId
          AND (c.isDeleted = FALSE OR c.isDeleted IS NULL)
          AND c.parentComment IS NULL
        ORDER BY c.createdAt DESC, c.id DESC
    """)

    List<Comment> firstPageCommentBlog(
            @Param("blogId") String blogId,
            Pageable pageable
    );

    @Query("""
        SELECT c FROM Comment c
        WHERE c.blog.id = :blogId
          AND (c.isDeleted = FALSE OR c.isDeleted IS NULL)
          AND (
                c.createdAt < :lastCreatedAt
                OR (c.createdAt = :lastCreatedAt AND c.id < :lastId)
          )
        ORDER BY c.createdAt DESC, c.id DESC
    """)
    List<Comment> nextPageCommentBlog(
            @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
            @Param("lastId") String lastId,
            @Param("blogId") String blogId,
            Pageable pageable
    );

}
