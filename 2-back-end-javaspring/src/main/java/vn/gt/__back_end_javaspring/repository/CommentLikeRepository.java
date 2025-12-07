package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.gt.__back_end_javaspring.entity.CommentLike;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, String> {

    boolean existsByUser_IdAndComment_Id(String userId, String commentId);

    Optional<CommentLike> findByUser_IdAndComment_Id(String userId, String commentId);

    long countByComment_Id(String commentId);

    List<CommentLike> findByComment_Id(String commentId);

    @Query("delete from CommentLike l where l.user.id = :userId and l.comment.id = :commentId")
    int deleteByUserAndComment(String userId, String commentId);
}
