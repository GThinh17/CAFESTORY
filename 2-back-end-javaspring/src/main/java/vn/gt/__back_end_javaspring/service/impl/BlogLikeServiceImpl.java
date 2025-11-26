package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.BlogLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogLikeResponse;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.BlogLike;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.exception.LikeExist;
import vn.gt.__back_end_javaspring.exception.LikeNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.BlogLikeMapper;
import vn.gt.__back_end_javaspring.repository.BlogRepository;
import vn.gt.__back_end_javaspring.repository.BlogLikeRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.BlogLikeService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogLikeServiceImpl implements BlogLikeService {
    private final BlogLikeRepository blogLikeRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final BlogLikeMapper blogLikeMapper;


    @Override
    public BlogLikeResponse like(BlogLikeCreateDTO request) {
        String userId = request.getUserId();
        String blogId = request.getBlogId();

        if(blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeExist("Like already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        System.out.println("DTO: " + request.toString());
        blog.setLikesCount(blog.getLikesCount() + 1);
        blogRepository.save(blog);

        BlogLike bloglike = blogLikeMapper.toModel(request);
        BlogLike saved =  blogLikeRepository.save(bloglike);

        return blogLikeMapper.toResponse(saved);

    }

    @Override
    public void unlike(String blogId, String userId) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        blog.setLikesCount(blog.getLikesCount() - 1);

        System.out.println("blogId: " + blogId + ", userId: " + userId);

        if(!blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeNotFoundException("Like not exists");
        }

        blogLikeRepository.deleteByUserAndBlog(userId, blogId);
    }

    @Override
    public boolean isLiked(String userId, String blogId) {
        return blogLikeRepository.existsByUser_IdAndBlog_id(userId, blogId);
    }

    @Override
    public long countLikes(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        return blogLikeRepository.countByBlog_Id(blogId);
    }

    @Override
    public List<BlogLikeResponse> getLikesByBlog(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        return blogLikeMapper.toResponseList(blogLikeRepository.findByBlog_Id(blogId));
    }
}
