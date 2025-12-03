package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.CommentLikeCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentLikeResponse;
import vn.gt.__back_end_javaspring.entity.Comment;
import vn.gt.__back_end_javaspring.entity.CommentLike;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.exception.CommentNotFoundException;
import vn.gt.__back_end_javaspring.exception.LikeExist;
import vn.gt.__back_end_javaspring.exception.LikeNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.CommentLikeMapper;
import vn.gt.__back_end_javaspring.repository.BlogLikeRepository;
import vn.gt.__back_end_javaspring.repository.CommentLikeRepository;
import vn.gt.__back_end_javaspring.repository.CommentRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.CommentLikeService;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final BlogLikeRepository blogLikeRepository;
    private  final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentLikeMapper commentLikeMapper;

    @Override
    public CommentLikeResponse likeComment(CommentLikeCreateDTO dto) {
        boolean exists = commentLikeRepository.existsById(dto.getCommentId());
        if (!exists) {
            throw new LikeExist("Like exists alreadyy");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));

        comment.setLikesCount(comment.getLikesCount() + 1);
        commentRepository.save(comment);

        CommentLike commentLike = commentLikeMapper.toModel(dto);
        CommentLike saved =  commentLikeRepository.save(commentLike);
        return commentLikeMapper.toResponse(saved);
    }

    @Override
    public void unlikeComment(String userId, String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        comment.setLikesCount(comment.getLikesCount() - 1);

        if(!commentLikeRepository.existsByUser_IdAndComment_Id(userId, commentId)) {
            throw new LikeNotFoundException("Like not exists alreadyy");
        }

        commentLikeRepository.deleteByUserAndComment(userId, commentId);

    }

    @Override
    public List<CommentLikeResponse> getLikesByComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));
        List<CommentLike> commentLikes = commentLikeRepository.findByComment_Id(commentId);

        return commentLikeMapper.toCommentResponseList(commentLikes);
    }

    @Override
    public boolean isLikedByUser(String userId, String commentId) {
        return commentLikeRepository.existsByUser_IdAndComment_Id(userId, commentId);
    }
}
