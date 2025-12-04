package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.PageAlbumCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageAlbumResponse;
import vn.gt.__back_end_javaspring.DTO.PageAlbumUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.PageAlbum;
import vn.gt.__back_end_javaspring.entity.PageImage;
import vn.gt.__back_end_javaspring.exception.PageAlbumNotFoundException;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.mapper.PageAlbumMapper;
import vn.gt.__back_end_javaspring.repository.PageAlbumRepository;
import vn.gt.__back_end_javaspring.repository.PageImageRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.service.PageAlbumService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PageAlbumServiceImpl implements PageAlbumService {
    private final PageAlbumRepository pageAlbumReposiroty;
    private final PageAlbumMapper pageAlbumMapper;
    private final PageRepository pageRepository;
    private final MappingsEndpoint mappingsEndpoint;
    private final PageImageRepository pageImageRepository;


    @Override
    public PageAlbumResponse createPageAlbum(PageAlbumCreateDTO dto) {
        PageAlbum pageAlbum = pageAlbumMapper.toEntity(dto);

        Page page = pageRepository.findById(dto.getPageId())
                .orElseThrow(()-> new PageNotFoundException("Page not found"));

        pageAlbum.setPage(page);
        PageAlbum saved =  pageAlbumReposiroty.save(pageAlbum);

        return pageAlbumMapper.toResponse(saved);
    }

    @Override
    public PageAlbumResponse getPageAlbumById(String albumId) {
        PageAlbum pageAlbum = pageAlbumReposiroty.findById(albumId)
                .orElseThrow(()-> new PageNotFoundException("Page not found"));
        return  pageAlbumMapper.toResponse(pageAlbum);
        }

    @Override
    public List<PageAlbumResponse> getAlbumsByPageId(String pageId) {
        Page page =  pageRepository.findById(pageId).orElseThrow(()-> new PageNotFoundException("Page not found"));
        List<PageAlbum> pageAlbums = pageAlbumReposiroty.findAllByIsDeletedFalseAndPageId(pageId);
        return pageAlbumMapper.toResponseList(pageAlbums);
    }

    @Override
    public PageAlbumResponse updatePageAlbum(String albumId, PageAlbumUpdateDTO dto) {
        PageAlbum pageAlbum = pageAlbumReposiroty.findById(albumId)
                .orElseThrow(()-> new PageAlbumNotFoundException("Page album not found"));
        pageAlbumMapper.updateFromDto(dto, pageAlbum);
        PageAlbum saved =  pageAlbumReposiroty.save(pageAlbum);
        return pageAlbumMapper.toResponse(pageAlbum);
    }

    @Override
    public PageAlbumResponse softDeleteAlbum(String albumId) {
        PageAlbum pageAlbum = pageAlbumReposiroty.findById(albumId)
                .orElseThrow(()-> new PageAlbumNotFoundException("Page album not found"));

        pageAlbum.setIsDeleted(true);
        PageAlbum saved =  pageAlbumReposiroty.save(pageAlbum);
        return pageAlbumMapper.toResponse(saved);
    }

    @Override
    public PageAlbumResponse addImageToAlbum(String albumId, List<String> imageUrls) {
        PageAlbum pageAlbum = pageAlbumReposiroty.findById(albumId)
                .orElseThrow(()-> new PageAlbumNotFoundException("Page album not found"));

        if(pageAlbum.getIsDeleted()==true){
            throw new PageAlbumNotFoundException("Page album has been deleted");
        }

        for(String imageUrl : imageUrls) {
            PageImage img = new PageImage();
            img.setImageUrl(imageUrl);
            img.setPageAlbum(pageAlbum);

            pageAlbum.getImages().add(img);
        }

        pageAlbum.setTotalPhoto(pageAlbum.getImages().size());
        pageAlbumReposiroty.save(pageAlbum);
        return pageAlbumMapper.toResponse(pageAlbum);
    }

    @Override
    public PageAlbumResponse removeImage(String albumId, String imageId) {
        PageAlbum pageAlbum = pageAlbumReposiroty.findById(albumId)
                .orElseThrow(()-> new PageAlbumNotFoundException("Page album not found"));

        PageImage image = pageImageRepository.findById(imageId)
                .orElseThrow(()-> new PageAlbumNotFoundException("Image not found"));

        pageAlbum.getImages().remove(image);
        pageImageRepository.delete(image);

        pageAlbum.setTotalPhoto(pageAlbum.getImages().size());

        PageAlbum saved =  pageAlbumReposiroty.save(pageAlbum);
        return pageAlbumMapper.toResponse(saved);

    }
}

