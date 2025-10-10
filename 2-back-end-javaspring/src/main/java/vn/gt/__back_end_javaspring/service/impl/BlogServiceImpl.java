package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.DTO.BlogRequest;
import vn.gt.__back_end_javaspring.entity.DTO.BlogResponse;
import vn.gt.__back_end_javaspring.mapper.BlogMapper;
import vn.gt.__back_end_javaspring.repository.BlogRepository;
import vn.gt.__back_end_javaspring.service.BlogService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    @Autowired
    private final BlogRepository blogRepository;

    @Autowired
    private final BlogMapper blogMapper;

    @Override
    public BlogResponse createBlog(BlogRequest blogRequest) {
        Blog blog = blogMapper.toModel(blogRequest);

        Blog saved = blogRepository.save(blog);
        return blogMapper.toResponse(saved);
    }

    @Override
    public List<BlogResponse> findNewestBlog() {
        List<Blog> blogs = blogRepository.findNewestBlogByCreatedAtDesc();
        List<BlogResponse> blogResponses = blogMapper.toResponseList(blogs);
        return blogResponses;
    }

    @Override
    public List<BlogResponse> findUserBlog(String userId) {
        List<Blog>  blogs = blogRepository.findBlogByUserId(userId);
        List<BlogResponse> blogResponses = blogMapper.toResponseList(blogs);
        return blogResponses;
    }
}
