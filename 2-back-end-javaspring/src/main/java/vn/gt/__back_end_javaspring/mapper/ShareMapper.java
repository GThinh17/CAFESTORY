package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.ShareRequestDTO;
import vn.gt.__back_end_javaspring.DTO.ShareResponseDTO;
import vn.gt.__back_end_javaspring.entity.*;

@Mapper(componentModel = "spring")
public interface ShareMapper {
    @Mapping(target = "visibility", source = "visibility")
    Share toEntity(ShareRequestDTO request);

    @Mapping(target = "shareId", source = "id")
    ShareResponseDTO toResponse(Share share);

}
