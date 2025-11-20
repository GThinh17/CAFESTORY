package vn.gt.__back_end_javaspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.gt.__back_end_javaspring.DTO.BlogCreateDTO;
import vn.gt.__back_end_javaspring.DTO.BlogResponse;
import vn.gt.__back_end_javaspring.DTO.BlogUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.CursorPage;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.mapper.BlogMapper;
import vn.gt.__back_end_javaspring.repository.BlogRepository;
import vn.gt.__back_end_javaspring.service.BlogService;
import vn.gt.__back_end_javaspring.util.CursorUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    @Override
    public BlogResponse createBlog(BlogCreateDTO blogCreateDTO) {
        Blog blog = blogMapper.toModel(blogCreateDTO);

        Blog saved = blogRepository.save(blog);
        return blogMapper.toResponse(saved);
    }

    @Override
    public void deleteBlog(String id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        // Soft delete để khớp với các query isDeleted = false
        blog.setIsDeleted(true);
        blogRepository.save(blog);
    }

//    @Override
//    public void deleteBlog(String id) {
//        Blog blog = blogRepository.findById(id)
//                .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));
//
//        blogRepository.delete(blog);
//    }

    @Override
    @Transactional(readOnly = true)
    public BlogResponse getBlogById(String id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));
        return blogMapper.toResponse(blog);
    }

    @Override
    public BlogResponse updateBlog(String id, BlogUpdateDTO blogUpdateDTO) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found!"));

        // MapStruct: ignore field null
        // GOi ham de the cac thay the cac field khac null,
        blogMapper.updateEntity(blog, blogUpdateDTO);

        // TODO: xử lý mediaUrls, locationId nếu cần (update mediaList, location)

        //Hibernate se check neu co id thi update ngc lai thi inseret
        Blog saved = blogRepository.save(blog);
        return blogMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CursorPage<BlogResponse> findNewestBlog(String cursor, int size) {
        List<Blog> blogs;
        Pageable pageRequest = PageRequest.of(0, size);

        if (cursor == null || cursor.isBlank()) {
            blogs = blogRepository.firstPage(pageRequest);
        } else {
            var p = CursorUtil.decode(cursor); // Pair<createdAt, id>
            blogs = blogRepository.nextPage(p.getLeft(), p.getRight(), pageRequest);
        }

        var items = blogMapper.toResponseList(blogs);

        String nextCursor = null;
        if (blogs.size() == size) {
            var last = blogs.get(blogs.size() - 1);
            nextCursor = CursorUtil.encode(last.getCreatedAt(), last.getId());
        }

        return CursorPage.<BlogResponse>builder()
                .data(items)
                .nextCursor(nextCursor)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CursorPage<BlogResponse> findUserBlog(String userId, String cursor, int size) {
        List<Blog> blogs;
        Pageable pageRequest = PageRequest.of(0, size);

        if (cursor == null || cursor.isBlank()) {
            blogs = blogRepository.firstUserPage(userId, pageRequest);
        } else {
            var p = CursorUtil.decode(cursor);
            blogs = blogRepository.nextUserPage(userId, p.getLeft(), p.getRight(), pageRequest);
        }

        var items = blogMapper.toResponseList(blogs);

        String nextCursor = null;
        if (blogs.size() == size) {
            var last = blogs.get(blogs.size() - 1);
            nextCursor = CursorUtil.encode(last.getCreatedAt(), last.getId());
        }

        return CursorPage.<BlogResponse>builder()
                .data(items)
                .nextCursor(nextCursor)
                .build();
    }
}


