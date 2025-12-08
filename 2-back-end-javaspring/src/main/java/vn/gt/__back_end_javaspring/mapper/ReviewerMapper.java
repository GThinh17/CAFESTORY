    package vn.gt.__back_end_javaspring.mapper;

    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
    import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
    import vn.gt.__back_end_javaspring.entity.Reviewer;

    @Mapper(componentModel = "spring")
    public interface ReviewerMapper {

        Reviewer toModel(ReviewerCreateDTO reviewerCreateDTO);

        @Mapping(source = "user.id", target = "userId")
        @Mapping(source = "user.fullName", target = "userName")
        @Mapping(source = "user.avatar", target = "userAvatarUrl")
        @Mapping(source = "user.email", target = "userEmail")
        ReviewerResponse toResponse(Reviewer reviewer);
    }
