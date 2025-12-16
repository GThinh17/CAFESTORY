package vn.gt.__back_end_javaspring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.entity.Notification;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toModel(NotificationRequestDTO notificationRequestDTO);

    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.fullName", target = "senderName")
    @Mapping(source = "sender.avatar", target = "senderAvatar")
    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponseList(List<Notification> notifications);
}
