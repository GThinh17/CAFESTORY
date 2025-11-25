package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.ShareCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ShareReponse;
import vn.gt.__back_end_javaspring.DTO.ShareUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Share;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShareMapper {

<<<<<<< HEAD

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "blog.id", source = "blogId")
    @Mapping(target = "location.id", source = "locationId")
    Share toEntity(ShareCreateDTO dto);



=======
    Share toEntity(ShareCreateDTO dto);

>>>>>>> origin/develop
    @Mapping(target = "shareId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", source = "user.fullName")
    @Mapping(target = "userAvatarUrl", source = "user.avatar")
    @Mapping(target = "blogId", source = "blog.id")
    @Mapping(target = "blogCaption", source = "blog.caption")
    @Mapping(target = "blogCreatedAt", source = "blog.createdAt")
    @Mapping(target = "blogIsDeleted", source = "blog.isDeleted")
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "locationName", source = "location.name")
    @Mapping(target = "addressLine", source = "location.addressLine")
    @Mapping(target = "ward", source = "location.ward")
    @Mapping(target = "province", source = "location.province")
    @Mapping(target = "longitude", source = "location.longitude")
    @Mapping(target = "latitude", source = "location.latitude")
    ShareReponse toResponse(Share share);

    List<ShareReponse> toResponseList(List<Share> shareList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ShareUpdateDTO dto, @MappingTarget Share share);

}
