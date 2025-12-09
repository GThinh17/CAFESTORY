package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.*;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.entity.Notification;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "receiver.id", target = "userId")
    @Mapping(source = "receiver.fullName", target = "userName")
    @Mapping(source = "actor.id", target = "actorId")
    @Mapping(source = "actor.fullName", target = "actorName")
    @Mapping(source = "actor.avatar", target = "actorAvatarUrl")
    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponseList(List<Notification> notifications);
}
