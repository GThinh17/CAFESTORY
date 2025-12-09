package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import vn.gt.__back_end_javaspring.entity.Page;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, String> {
    Page findPageByCafeOwner_Id(String cafeOwnerId);

    @Query("SELECT p FROM Page p ORDER BY p.followingCount DESC")
    List<Page> findAllOrderByFollowersDesc();

}