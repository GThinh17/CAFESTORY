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
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.mapper.BlogMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.BlogService;
import vn.gt.__back_end_javaspring.service.CafeOwnerService;
import vn.gt.__back_end_javaspring.service.NotificationService;
import vn.gt.__back_end_javaspring.util.CursorUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;
    private final locationRepository locationRepository;
    private final UserRoleRepository userRoleRepository;
    private final NotificationService notificationService;
    private final CafeOwnerService cafeOwnerService;

    @Override
    public BlogResponse createBlog(BlogCreateDTO dto) {
        Blog blog = blogMapper.toModel(dto);


        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        blog.setUser(user);

        Page page = null;
        if (dto.getPageId() != null) {
            page = pageRepository.findById(dto.getPageId())
                    .orElseThrow(() -> new RuntimeException("Page not found with id: " + dto.getPageId()));
            blog.setPage(page);
        }

        if (dto.getLocationId() != null) {
            var location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new RuntimeException("Location not found with id: " + dto.getLocationId()));
            blog.setLocation(location);
        }

        if (dto.getMediaUrls() != null && !dto.getMediaUrls().isEmpty()) {
            List<Media> mediaList = dto.getMediaUrls().stream()
                    .map(url -> {
                        Media m = new Media();
                        m.setMediaUrl(url);
                        m.setBlog(blog);
                        return m;
                    })
                    .toList();
            blog.setMediaList(mediaList);
        }


        Blog saved = blogRepository.save(blog);

        if (page != null && cafeOwnerService.isCafeOwner(dto.getUserId())) {
            notificationService.notifyPageNewPost(page, saved);
        }

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

    // @Override
    // public void deleteBlog(String id) {
    // Blog blog = blogRepository.findById(id)
    // .orElseThrow(()-> new BlogNotFoundException("Blog not found!"));
    //
    // blogRepository.delete(blog);
    // }

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

        // Hibernate se check neu co id thi update ngc lai thi inseret
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

        System.out.println("Blog size: " + blogs.size());
        System.out.println("size: " + size);
        String nextCursor = null;
        if (blogs.size() == size) {
            var last = blogs.get(blogs.size() - 1);
            nextCursor = CursorUtil.encode(last.getCreatedAt(), last.getId());
            System.out.println("nextCursor: " + nextCursor);
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
