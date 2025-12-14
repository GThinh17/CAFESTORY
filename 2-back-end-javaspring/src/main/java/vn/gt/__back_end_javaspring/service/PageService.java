package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Page;

import java.util.List;

public interface PageService {

    PageResponse createPage(PageCreateDTO request);

    PageResponse getPageById(String pageId);

    PageResponse getPageByCafeOwnerId(String cafeOwnerId);

    PageResponse updatePage(PageUpdateDTO request, String pageId);

    void deletePage(String pageId);


    public List<PageResponse> getAllPagesOrderByFollowersDesc();

    List<PageResponse> getAllPagesFollowedByUser(String userId);

    List<PageResponse> getAllPagesFollowedByUserSortedAsc(String userId);

    String getCafeOwnerId(String pageId);
}
