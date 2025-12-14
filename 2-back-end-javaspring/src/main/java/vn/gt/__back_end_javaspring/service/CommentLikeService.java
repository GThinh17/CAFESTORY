package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.CommentLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentLikeResponse;

import java.util.List;

public interface CommentLikeService {

    CommentLikeResponse likeComment(CommentLikeCreateDTO dto);

    void unlikeComment(String userId, String commentId);

    List<CommentLikeResponse> getLikesByComment(String commentId);

    boolean isLikedByUser(String userId, String commentId);

}
