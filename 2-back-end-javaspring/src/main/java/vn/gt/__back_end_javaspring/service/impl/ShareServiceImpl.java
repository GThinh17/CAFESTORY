package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.ShareRequestDTO;
import vn.gt.__back_end_javaspring.DTO.ShareResponseDTO;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.mapper.ShareMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.ShareService;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final ShareDetailRepository shareDetailRepository;
    private final ShareMapper shareMapper;



    @Override
    @Transactional
    public ShareResponseDTO createShare(ShareRequestDTO request) {
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var blog = blogRepository.findById(request.getBlogId())
                .orElseThrow(() -> new IllegalArgumentException("Blog not found"));

        if (shareDetailRepository.existsByUser_IdAndBlog_Id(user.getId(), blog.getId())) {
            throw new IllegalStateException("Already shared");
        }

        var share = shareMapper.toEntity(request);
        share = shareRepository.save(share);

        var id = new ShareDetailId();
        id.setShareId(share.getId());
        id.setUserId(user.getId());
        id.setBlogId(blog.getId());

        var detail = new ShareDetail();
        detail.setId(id);
        detail.setShare(share);
        detail.setUser(user);
        detail.setBlog(blog);
        shareDetailRepository.save(detail);

        System.out.println();

        blog.setBlogShare(blog.getBlogShare() + 1);
        blogRepository.save(blog);

        return shareMapper.toResponse(share);
    }

    @Override
    @Transactional
    public boolean hasShared(String userId, String blogId) {
        return shareDetailRepository.existsByUser_IdAndBlog_Id(userId, blogId);
    }
}
