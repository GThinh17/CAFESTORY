package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.PageAlbumCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageAlbumResponse;
import vn.gt.__back_end_javaspring.DTO.PageAlbumUpdateDTO;
import vn.gt.__back_end_javaspring.DTO.PageImageResponseDTO;
import vn.gt.__back_end_javaspring.entity.PageAlbum;
import vn.gt.__back_end_javaspring.entity.PageImage;
import vn.gt.__back_end_javaspring.enums.Visibility;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageAlbumMapper {

    @Mapping(source = "id", target = "pageAlbumId")
    @Mapping(source = "page.id", target = "pageId")
    @Mapping(source = "visibility", target = "visibility")

    PageAlbumResponse toResponse(PageAlbum album);

    @Mapping(source = "id", target = "pageImageId")
    PageImageResponseDTO toImageResponseDTO(PageImage pageImage);

    PageAlbum toEntity(PageAlbumCreateDTO dto);

    List<PageAlbumResponse> toResponseList(List<PageAlbum> albums);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PageAlbumUpdateDTO dto, @MappingTarget PageAlbum album);

}
