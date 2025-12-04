package vn.gt.__back_end_javaspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;

import java.util.List;

public interface PageService {

    PageResponse createPage(PageCreateDTO request);

    PageResponse getPageById(String pageId);

    PageResponse getPageByUserId(String userId);

    PageResponse updatePage(PageUpdateDTO request, String pageId);

    void deletePage(String pageId);

}
