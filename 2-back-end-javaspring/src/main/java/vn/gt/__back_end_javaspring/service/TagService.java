package vn.gt.__back_end_javaspring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.gt.__back_end_javaspring.DTO.TagDTO;
import vn.gt.__back_end_javaspring.DTO.TagResponse;
import vn.gt.__back_end_javaspring.entity.Tag;

@Service
public interface TagService {

    public List<?> GetAllTag();

    public TagResponse GetTagById(String id);

    public TagResponse CreateTag(TagDTO tagDTO);

    public TagResponse UpdateTag(String id, TagDTO tagDTO);

    public Tag DeleteTagById(String id);
}
