package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.LikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.LikeResponse;

import java.util.List;

public interface LikeService {

    LikeResponse like(LikeCreateDTO request);

    void unlike(String userId, String blogId);

    boolean isLiked(String userId, String blogId);

    long countLikes(String blogId);

    List<LikeResponse> getLikesByBlog(String blogId);
}
