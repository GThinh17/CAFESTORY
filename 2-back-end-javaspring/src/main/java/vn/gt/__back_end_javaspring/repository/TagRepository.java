package vn.gt.__back_end_javaspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, String> {
    public List<Tag> findByUserTag_Id(String userIdTag);
}
