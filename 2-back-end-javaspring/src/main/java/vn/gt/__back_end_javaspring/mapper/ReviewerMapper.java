package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.DTO.ReviewerUpdateDTO;
import vn.gt.__back_end_javaspring.entity.Reviewer;

@Mapper(componentModel = "spring")
public interface ReviewerMapper {

    Reviewer toModel(ReviewerCreateDTO reviewerCreateDTO);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userName")
    @Mapping(source = "user.avatar", target = "userAvatarUrl")
    @Mapping(source = "user.email", target = "userEmail")
    ReviewerResponse toResponse(Reviewer reviewer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ReviewerUpdateDTO dto, @MappingTarget Reviewer entity);
}
