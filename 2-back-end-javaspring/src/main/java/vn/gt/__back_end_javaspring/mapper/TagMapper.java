package vn.gt.__back_end_javaspring.mapper;

import vn.gt.__back_end_javaspring.DTO.TagResponse;
import vn.gt.__back_end_javaspring.entity.Tag;

public class TagMapper {

    public static TagResponse toTagResponse(Tag tag) {

        if (tag == null)
            return null;

        TagResponse res = new TagResponse();

        // tag id
        res.setId(tag.getId());

        // người tạo tag
        if (tag.getUserId() != null) {
            res.setUserId(tag.getUserId().getId());
        }

        // blog
        if (tag.getBlogIdTag() != null) {
            res.setBlogIdTag(tag.getBlogIdTag().getId());
        }

        // username hiển thị
        res.setUsername(tag.getUsername());

        // tag USER
        if (tag.getUserIdTag() != null) {
            res.setUserIdTag(tag.getUserIdTag().getId());
            res.setPageIdTag(null);
        }

        // tag PAGE
        if (tag.getPageIdTag() != null) {
            res.setPageIdTag(tag.getPageIdTag().getId());
            res.setUserIdTag(null);
        }

        return res;
    }
}
