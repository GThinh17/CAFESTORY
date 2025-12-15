package vn.gt.__back_end_javaspring.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.TagDTO;
import vn.gt.__back_end_javaspring.DTO.TagResponse;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.Tag;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.exception.TagNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.TagMapper;
import vn.gt.__back_end_javaspring.repository.BlogRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.TagRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.TagService;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PageRepository pageRepository;

    @Override
    public List<?> GetAllTag() {
        return this.tagRepository.findAll();
    }

    @Override
    public Tag GetTagById(String id) {
        Tag tag = this.tagRepository.findById(id).orElseThrow(() -> new TagNotFound("Tag Not Found"));
        return tag;
    }

    @Override
    public TagResponse CreateTag(TagDTO tagDTO) {

        // 1. Lấy blog
        Blog blog = blogRepository.findById(tagDTO.getBlogIdTag())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

        // 2. Tạo Tag entity
        Tag tag = new Tag();
        tag.setBlogIdTag(blog);

        // 3. Người tạo tag
        if (tagDTO.getUserId() != null) {
            User creator = userRepository.findById(tagDTO.getUserId())
                    .orElseThrow(() -> new BlogNotFoundException("User not found"));
            tag.setUserId(creator);
        }

        // 4. TAG PAGE
        if (tagDTO.getPageIdTag() != null) {

            Page page = pageRepository.findById(tagDTO.getPageIdTag())
                    .orElseThrow(() -> new BlogNotFoundException("Page not found"));

            tag.setPageIdTag(page);
            tag.setUserIdTag(null);
            tag.setUsername(page.getPageName());
        }
        // 5. TAG USER
        else if (tagDTO.getUserIdTag() != null) {

            User userTag = userRepository.findById(tagDTO.getUserIdTag())
                    .orElseThrow(() -> new BlogNotFoundException("User not found"));

            tag.setUserIdTag(userTag);
            tag.setPageIdTag(null);
            tag.setUsername(userTag.getFullName());
        } else {
            throw new IllegalArgumentException("Must tag a user or a page");
        }

        // 6. Lưu DB
        Tag savedTag = tagRepository.save(tag);

        // 7. Mapper → Response
        return TagMapper.toTagResponse(savedTag);
    }

    @Override
    public TagResponse UpdateTag(String id, TagDTO tagDTO) {

        // 1. Lấy tag cần update
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Tag not found"));

        // 2. Update blog (nếu có)
        if (tagDTO.getBlogIdTag() != null) {
            Blog blog = blogRepository.findById(tagDTO.getBlogIdTag())
                    .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
            tag.setBlogIdTag(blog);
        }

        // 3. Update TAG PAGE
        if (tagDTO.getPageIdTag() != null) {

            Page page = pageRepository.findById(tagDTO.getPageIdTag())
                    .orElseThrow(() -> new BlogNotFoundException("Page not found"));

            tag.setPageIdTag(page);
            tag.setUserIdTag(null);
            tag.setUsername(page.getPageName());
        }
        // 4. Update TAG USER
        else if (tagDTO.getUserIdTag() != null) {

            User user = userRepository.findById(tagDTO.getUserIdTag())
                    .orElseThrow(() -> new BlogNotFoundException("User not found"));

            tag.setUserIdTag(user);
            tag.setPageIdTag(null);
            tag.setUsername(user.getFullName());
        }

        // 5. Lưu DB
        Tag updatedTag = tagRepository.save(tag);

        // 6. Mapper → Response
        return TagMapper.toTagResponse(updatedTag);
    }

    @Override
    public Tag DeleteTagById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DeleteTagById'");
    }

}
