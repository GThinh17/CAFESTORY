package vn.gt.__back_end_javaspring.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.PageCreateDTO;
import vn.gt.__back_end_javaspring.DTO.PageResponse;
import vn.gt.__back_end_javaspring.DTO.PageUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

//    @Mapping(source = "cafeOwnerId", target = "cafeOwner.id")
//    @Mapping(target = "openHours", source = "openHours")
    Page toModel(PageCreateDTO request);

    @Mapping(source = "id", target = "pageId")
    @Mapping(source = "cafeOwner.businessName", target = "businessName")
    PageResponse toResponse(Page page);

    default String map(JsonNode node) {
        return node != null ? node.toString() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Page page, PageUpdateDTO request);
}
