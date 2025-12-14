package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.PageAlbumCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageAlbumResponse;
import vn.gt.__back_end_javaspring.DTO.PageAlbumUpdateDTO;

import java.util.List;

public interface PageAlbumService {

    PageAlbumResponse createPageAlbum(PageAlbumCreateDTO dto);

    PageAlbumResponse getPageAlbumById(String albumId);

    List<PageAlbumResponse> getAlbumsByPageId(String pageId);

    PageAlbumResponse updatePageAlbum(String albumId, PageAlbumUpdateDTO dto);

    PageAlbumResponse softDeleteAlbum(String albumId);

    PageAlbumResponse addImageToAlbum(String albumId, List<String> imageUrls);

    PageAlbumResponse removeImage(String albumId, String imageId);
}
