package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.PageAlbumCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageAlbumResponse;
import vn.gt.__back_end_javaspring.DTO.PageAlbumUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.PageImageResponseDTO;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.PageAlbum;
import vn.gt.__back_end_javaspring.entity.PageImage;
import vn.gt.__back_end_javaspring.enums.Visibility;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-14T22:41:56+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class PageAlbumMapperImpl implements PageAlbumMapper {

    @Override
    public PageAlbumResponse toResponse(PageAlbum album) {
        if ( album == null ) {
            return null;
        }

        PageAlbumResponse.PageAlbumResponseBuilder pageAlbumResponse = PageAlbumResponse.builder();

        pageAlbumResponse.pageAlbumId( album.getId() );
        pageAlbumResponse.pageId( albumPageId( album ) );
        if ( album.getVisibility() != null ) {
            pageAlbumResponse.visibility( album.getVisibility().name() );
        }
        pageAlbumResponse.title( album.getTitle() );
        pageAlbumResponse.images( pageImageListToPageImageResponseDTOList( album.getImages() ) );
        pageAlbumResponse.totalPhoto( album.getTotalPhoto() );
        pageAlbumResponse.isDeleted( album.getIsDeleted() );

        return pageAlbumResponse.build();
    }

    @Override
    public PageImageResponseDTO toImageResponseDTO(PageImage pageImage) {
        if ( pageImage == null ) {
            return null;
        }

        PageImageResponseDTO.PageImageResponseDTOBuilder pageImageResponseDTO = PageImageResponseDTO.builder();

        pageImageResponseDTO.pageImageId( pageImage.getId() );
        pageImageResponseDTO.imageUrl( pageImage.getImageUrl() );

        return pageImageResponseDTO.build();
    }

    @Override
    public PageAlbum toEntity(PageAlbumCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PageAlbum pageAlbum = new PageAlbum();

        pageAlbum.setTitle( dto.getTitle() );
        if ( dto.getVisibility() != null ) {
            pageAlbum.setVisibility( Enum.valueOf( Visibility.class, dto.getVisibility() ) );
        }

        return pageAlbum;
    }

    @Override
    public List<PageAlbumResponse> toResponseList(List<PageAlbum> albums) {
        if ( albums == null ) {
            return null;
        }

        List<PageAlbumResponse> list = new ArrayList<PageAlbumResponse>( albums.size() );
        for ( PageAlbum pageAlbum : albums ) {
            list.add( toResponse( pageAlbum ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PageAlbumUpdateDTO dto, PageAlbum album) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            album.setTitle( dto.getTitle() );
        }
        if ( dto.getVisibility() != null ) {
            album.setVisibility( Enum.valueOf( Visibility.class, dto.getVisibility() ) );
        }
        if ( dto.getIsDeleted() != null ) {
            album.setIsDeleted( dto.getIsDeleted() );
        }
    }

    private String albumPageId(PageAlbum pageAlbum) {
        Page page = pageAlbum.getPage();
        if ( page == null ) {
            return null;
        }
        return page.getId();
    }

    protected List<PageImageResponseDTO> pageImageListToPageImageResponseDTOList(List<PageImage> list) {
        if ( list == null ) {
            return null;
        }

        List<PageImageResponseDTO> list1 = new ArrayList<PageImageResponseDTO>( list.size() );
        for ( PageImage pageImage : list ) {
            list1.add( toImageResponseDTO( pageImage ) );
        }

        return list1;
    }
}
