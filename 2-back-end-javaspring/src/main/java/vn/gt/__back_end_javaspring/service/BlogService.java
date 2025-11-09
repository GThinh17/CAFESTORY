package vn.gt.__back_end_javaspring.service;

import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.entity.DTO.BlogRequest;
import vn.gt.__back_end_javaspring.entity.DTO.BlogResponse;

import java.util.List;


public interface BlogService {
    public BlogResponse createBlog(BlogRequest blogRequest) ;
    public List<BlogResponse> findNewestBlog();
    public List<BlogResponse> findUserBlog(String userId);
}
