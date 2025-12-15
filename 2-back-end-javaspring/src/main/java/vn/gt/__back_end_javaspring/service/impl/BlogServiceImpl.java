package vn.gt.__back_end_javaspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.gt.__back_end_javaspring.DTO.*;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.NotificationType;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.exception.ReviewerNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.BlogMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.BlogService;
import vn.gt.__back_end_javaspring.service.CafeOwnerService;
import vn.gt.__back_end_javaspring.service.FollowService;
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
    private final CafeOwnerService cafeOwnerService;
    private final ReviewerRepository reviewerRepository;
    private final FollowRepository followRepository;
    private final FollowService followService;
    private final NotificationService notificationService;

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
            List<FollowResponse> follower = followService.getPageFollower(page.getId());
            for(FollowResponse follow : follower){
                String receivedId = follow.getFollowerId();


                NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
                notificationRequestDTO.setSenderId(user.getId());
                notificationRequestDTO.setReceiverId(receivedId);
                notificationRequestDTO.setType(NotificationType.PAGE_NEW_POST);
                notificationRequestDTO.setPostId(saved.getId());
                notificationRequestDTO.setCommentId(null);
                notificationRequestDTO.setPageId(null);
                notificationRequestDTO.setWalletTransactionId(null);
                notificationRequestDTO.setBadgeId(null);
                notificationRequestDTO.setBody(page.getPageName()+ "đăng bài post mới");

                notificationService.sendNotification(receivedId, notificationRequestDTO);
//                notificationClient.sendNotification(notificationRequestDTO);
            }
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
    public org.springframework.data.domain.Page<BlogResponse> getBlogsForUser(String userId, PageRequest pageRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        org.springframework.data.domain.Page<Blog> blogResponses = blogRepository.
                findByUser_IdAndPage_IdNullOrderByCreatedAtDescIdDesc(userId, pageRequest);

        return  blogResponses.map(blogMapper::toResponse);
    }

    @Override
    public org.springframework.data.domain.Page<BlogResponse> getBlogsForReviewer(String reviewerId, PageRequest  pageRequest) {
        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(()-> new ReviewerNotFound("Reviewer Not found"));
        String userId = reviewer.getUser().getId();

        org.springframework.data.domain.Page<Blog> blogResponsePage = blogRepository.
                findByUser_IdAndPage_IdNullOrderByCreatedAtDescIdDesc(userId, pageRequest);

        return blogResponsePage.map(blogMapper::toResponse);

    }

    @Override
    public org.springframework.data.domain.Page<BlogResponse> getBlogsForPage(String pageId, PageRequest pageRequest) {
        Page page = pageRepository.findById(pageId)
                .orElseThrow(()-> new PageNotFoundException("Page not found"));

        org.springframework.data.domain.Page<Blog>blogPage = blogRepository.findByPage_IdOrderByCreatedAtDescIdDesc(pageId, pageRequest);
        return blogPage.map(blogMapper::toResponse);
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
