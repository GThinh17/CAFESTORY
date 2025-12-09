package vn.gt.__back_end_javaspring.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.entity.Notification;
import vn.gt.__back_end_javaspring.entity.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T19:09:10+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class NotificationMapperImpl implements NotificationMapper {

    @Override
    public NotificationResponse toResponse(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationResponse notificationResponse = new NotificationResponse();

        notificationResponse.setUserId( notificationReceiverId( notification ) );
        notificationResponse.setUserName( notificationReceiverFullName( notification ) );
        notificationResponse.setActorId( notificationActorId( notification ) );
        notificationResponse.setActorName( notificationActorFullName( notification ) );
        notificationResponse.setActorAvatarUrl( notificationActorAvatar( notification ) );
        notificationResponse.setId( notification.getId() );
        notificationResponse.setType( notification.getType() );
        notificationResponse.setTitle( notification.getTitle() );
        notificationResponse.setContent( notification.getContent() );
        notificationResponse.setRedirectUrl( notification.getRedirectUrl() );
        if ( notification.getRead() != null ) {
            notificationResponse.setRead( notification.getRead() );
        }
        notificationResponse.setCreatedAt( notification.getCreatedAt() );

        return notificationResponse;
    }

    @Override
    public List<NotificationResponse> toResponseList(List<Notification> notifications) {
        if ( notifications == null ) {
            return null;
        }

        List<NotificationResponse> list = new ArrayList<NotificationResponse>( notifications.size() );
        for ( Notification notification : notifications ) {
            list.add( toResponse( notification ) );
        }

        return list;
    }

    private String notificationReceiverId(Notification notification) {
        User receiver = notification.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        return receiver.getId();
    }

    private String notificationReceiverFullName(Notification notification) {
        User receiver = notification.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        return receiver.getFullName();
    }

    private String notificationActorId(Notification notification) {
        User actor = notification.getActor();
        if ( actor == null ) {
            return null;
        }
        return actor.getId();
    }

    private String notificationActorFullName(Notification notification) {
        User actor = notification.getActor();
        if ( actor == null ) {
            return null;
        }
        return actor.getFullName();
    }

    private String notificationActorAvatar(Notification notification) {
        User actor = notification.getActor();
        if ( actor == null ) {
            return null;
        }
        return actor.getAvatar();
    }
}
