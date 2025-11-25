package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.*;

import java.util.List;


public interface BlogService {
     BlogResponse createBlog(BlogCreateDTO blogCreateDTO) ;
     CursorPage<BlogResponse> findNewestBlog(String cursor, int size);
     CursorPage<BlogResponse> findUserBlog(String userId, String cursor, int size);
     void deleteBlog(String id);
     BlogResponse getBlogById(String id);
     BlogResponse updateBlog(String id, BlogUpdateDTO blogUpdateDTO);
}
