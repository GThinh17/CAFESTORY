package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.PageAlbum;

import java.util.List;

public interface PageAlbumRepository extends JpaRepository<PageAlbum, String> {
    List<PageAlbum> findAllByIsDeletedFalseAndPageId(String pageId);
}
