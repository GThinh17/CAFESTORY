package vn.gt.__back_end_javaspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.gt.__back_end_javaspring.DTO.CommentCreateDTO;
import vn.gt.__back_end_javaspring.DTO.CommentResponse;
import vn.gt.__back_end_javaspring.DTO.CommentUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.CursorPage;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Comment;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.exception.CommentNotFoundException;
import vn.gt.__back_end_javaspring.mapper.CommentMapper;
import vn.gt.__back_end_javaspring.repository.BlogRepository;
import vn.gt.__back_end_javaspring.repository.CommentRepository;
import vn.gt.__back_end_javaspring.service.CommentService;
import vn.gt.__back_end_javaspring.util.CursorUtil;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public CursorPage<CommentResponse> getCommentsNewestByBlogId(String blogId, String cursor, int size) {
       List<Comment> comments;
        Blog blog = blogRepository.findById(blogId).
                orElseThrow(() -> new BlogNotFoundException("Blog not found"));

        Pageable pageRequest = PageRequest.of(0, size);
        if(cursor == null || cursor.isBlank()) {
            comments = commentRepository.firstPageCommentBlog(blog.getId(), pageRequest);
        } else{
            var p = CursorUtil.decode(cursor);
            var lastCreatedAt = p.getLeft();
            var lastId = p.getRight();
            comments = commentRepository.nextPageCommentBlog(lastCreatedAt, lastId, blogId, pageRequest);
        }
        var items = commentMapper.toResponseList(comments);
        String nextCursor = null;
        if(comments.size() == size){
            var last = comments.get(comments.size() - 1);
            nextCursor = CursorUtil.encode(last.getCreatedAt(), last.getId());
        }

        return CursorPage.<CommentResponse>builder().
                data(items).
                nextCursor(nextCursor).
                build();
    }

    @Override
    public CommentResponse getCommentById(String commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        return commentMapper.toResponse(comment);

    }


    @Override
    public CommentResponse addComment(CommentCreateDTO dto) {

        System.out.println(">>> Blog ID nhận được: " + dto.getBlogId());

        Blog blog = blogRepository.findById(dto.getBlogId())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

        System.out.println(">>> Blog tìm thấy trong DB: " + blog.getId());
        Comment comment = commentMapper.toModel(dto);
        comment = commentRepository.save(comment);
        return commentMapper.toResponse(comment);
    }

    @Override
    public CommentResponse updateComment(String commentId, CommentUpdateDTO commentUpdateDTO) {
        Comment comment =  commentRepository.findById(commentId).
                orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        commentMapper.updateEntity(comment, commentUpdateDTO);
        Comment saved = commentRepository.save(comment);
        return commentMapper.toResponse(saved);
    }

//    @Override //HardDelete
//    public CommentResponse deleteComment(String commentId) {
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
//                commentRepository.delete(comment);
//                return commentMapper.toResponse(comment);
//    }

    //Soft Delete
    @Override
    public CommentResponse deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setIsDeleted(true);
        commentRepository.save(comment);
        return commentMapper.toResponse(commentRepository.save(comment));
    }
}

