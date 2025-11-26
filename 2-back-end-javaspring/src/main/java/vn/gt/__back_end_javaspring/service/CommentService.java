package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.CommentCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentResponse;
import vn.gt.__back_end_javaspring.DTO.CommentUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.CursorPage;

public interface CommentService {
    CursorPage<CommentResponse> getCommentsNewestByBlogId(String blogId, String cursor, int size);
    CommentResponse getCommentById(String commentId);
    CommentResponse addComment(CommentCreateDTO dto);
    CommentResponse updateComment(String commentId, CommentUpdateDTO dto);
    CommentResponse deleteComment(String commentId);
}
