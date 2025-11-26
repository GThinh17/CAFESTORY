package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import vn.gt.__back_end_javaspring.entity.Page;

public interface PageRepository extends JpaRepository<Page, String> {
    Page findPageByUser_Id(String user_id);
}
