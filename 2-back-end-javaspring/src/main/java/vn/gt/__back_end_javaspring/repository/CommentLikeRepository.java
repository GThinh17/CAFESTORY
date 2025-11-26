package vn.gt.__back_end_javaspring.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.gt.__back_end_javaspring.entity.CommentLike;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, String> {

    boolean existsByUser_IdAndComment_Id(String userId, String commentId);

    Optional<CommentLike> findByUser_IdAndComment_Id(String userId, String commentId);

    long countByComment_Id(String commentId);

    List<CommentLike> findByComment_Id(String commentId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CommentLike l WHERE l.user.id = :userId AND l.comment.id = :commentId")
    int deleteByUserIdAndCommentId(@Param("userId") String userId,
                                   @Param("commentId") String commentId);
}
