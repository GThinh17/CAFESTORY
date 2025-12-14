package vn.gt.__back_end_javaspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import vn.gt.__back_end_javaspring.DTO.*;

import java.util.List;

public interface BlogService {
     BlogResponse createBlog(BlogCreateDTO blogCreateDTO);

     CursorPage<BlogResponse> findNewestBlog(String cursor, int size);

     CursorPage<BlogResponse> findUserBlog(String userId, String cursor, int size);

     void deleteBlog(String id);

     BlogResponse getBlogById(String id);

     BlogResponse updateBlog(String id, BlogUpdateDTO blogUpdateDTO);

     Page<BlogResponse> getBlogsForUser(String userId, PageRequest pageRequest);

     Page<BlogResponse> getBlogsForReviewer(String reviewerId, PageRequest pageRequest);

     Page<BlogResponse> getBlogsForPage(String pageId, PageRequest pageRequest);
}
