package vn.gt.__back_end_javaspring.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "openHours", source = "openHours")
    Page toModel(PageCreateDTO request) ;

    @Mapping(source = "id", target = "pageId")
    @Mapping(source = "user.id", target = "userId")
    PageResponse toResponse(Page page);

    default String map(JsonNode node) {
        return node != null ? node.toString() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Page page, PageUpdateDTO request);
}
