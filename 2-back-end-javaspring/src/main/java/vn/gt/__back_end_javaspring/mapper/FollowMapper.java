package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.FollowCreateDTO;
import vn.gt.__back_end_javaspring.DTO.FollowResponse;
import vn.gt.__back_end_javaspring.entity.Follow;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FollowMapper {
    Follow toModel(FollowCreateDTO followCreateDTO);

    @Mapping(source = "id", target = "followId")
    @Mapping(source = "follower.id", target = "followerId")
    @Mapping(source = "follower.fullName", target = "followerFullName")
    @Mapping(source = "follower.avatar", target = "followerAvatar")
    @Mapping(source = "followedUser.id", target = "userFollowedId")
    @Mapping(source = "followedUser.fullname", target = "userFollowedFullName")
    @Mapping(source = "followedUser.avatar", target = "userFollowedAvatar")
    @Mapping(source = "followedPage.id", target = "pageFollowedId")
    @Mapping(source = "followedPage.pageName", target = "pageFollowedName")
    @Mapping(source = "followedPage.avatarUrl", target = "pageFollowedAvatar")
    @Mapping(source = "createdAt", target = "createdAt")
    FollowResponse toResponse(Follow follow);

    List<FollowResponse> toResponse(List<Follow> follows);
}

