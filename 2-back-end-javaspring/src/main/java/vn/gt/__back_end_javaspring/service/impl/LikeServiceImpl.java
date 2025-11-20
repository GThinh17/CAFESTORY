package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.LikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.LikeResponse;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Like;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.exception.LikeExist;
import vn.gt.__back_end_javaspring.exception.LikeNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.LikeMapper;
import vn.gt.__back_end_javaspring.repository.BlogRepository;
import vn.gt.__back_end_javaspring.repository.LikeRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.LikeService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final LikeMapper likeMapper;


    @Override
    public LikeResponse like(LikeCreateDTO request) {
        String userId = request.getUserId();
        String blogId = request.getBlogId();

        if(likeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeExist("Like already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        Like like = likeMapper.toModel(request);
        Like saved =  likeRepository.save(like);

        return likeMapper.toResponse(saved);

    }

    @Override
    public void unlike(String blogId, String userId) {
        if(!likeRepository.existsByUser_IdAndBlog_id(userId, blogId)) {
            throw new LikeNotFoundException("Like not exists");
        }
        likeRepository.deleteByUserAndBlog(userId, blogId);
    }

    @Override
    public boolean isLiked(String userId, String blogId) {
        return likeRepository.existsByUser_IdAndBlog_id(userId, blogId);
    }

    @Override
    public long countLikes(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        return likeRepository.countByBlog_Id(blogId);
    }

    @Override
    public List<LikeResponse> getLikesByBlog(String blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));

        return likeMapper.toResponseList(likeRepository.findByBlog_Id(blogId));
    }
}
