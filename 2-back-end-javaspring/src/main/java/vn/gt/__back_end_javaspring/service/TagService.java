package vn.gt.__back_end_javaspring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.gt.__back_end_javaspring.entity.Tag;

@Service
public interface TagService {

    public List<?> GetAllTag();

    public Tag GetTagById();

    public Tag CreateTag();

    public Tag UpdateTag(String id, Tag tag);
}
