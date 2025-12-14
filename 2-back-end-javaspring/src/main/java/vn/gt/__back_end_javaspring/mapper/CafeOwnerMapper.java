package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerDTO;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerResponse;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.CafeOwner;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CafeOwnerMapper {
    CafeOwner toEntity(CafeOwnerDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.avatar", target = "userAvatar")
    CafeOwnerResponse toResponse(CafeOwner entity);

    List<CafeOwnerResponse> toResponseList(List<CafeOwner> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CafeOwnerUpdateDTO dto, @MappingTarget CafeOwner entity);
}
