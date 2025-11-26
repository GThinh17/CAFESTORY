package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.BlogLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogLikeResponse;

import java.util.List;

public interface BlogLikeService {

    BlogLikeResponse like(BlogLikeCreateDTO request);

    void unlike(String userId, String blogId);

    boolean isLiked(String userId, String blogId);

    long countLikes(String blogId);

    List<BlogLikeResponse> getLikesByBlog(String blogId);
}
