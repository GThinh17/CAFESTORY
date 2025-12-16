package vn.gt.__back_end_javaspring.service.impl;

import com.google.firebase.messaging.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.config.FirebaseConfig;
import vn.gt.__back_end_javaspring.entity.User;

import vn.gt.__back_end_javaspring.entity.UserDevice;
import vn.gt.__back_end_javaspring.exception.NotificationNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.NotificationMapper;
import vn.gt.__back_end_javaspring.repository.NotificationRepository;
import vn.gt.__back_end_javaspring.repository.UserDeviceRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.NotificationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private final UserDeviceRepository userDeviceRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void sendNotification(String receiverId, NotificationRequestDTO dto) {
        try{
            System.out.println("User device toi day roio "+ receiverId);

            UserDevice userDevice = userDeviceRepository.findUserDeviceByUser_Id(receiverId);// Find token

            if (userDevice == null) {
                return;
            }


            System.out.println("User device "+ userDevice.getFcmToken());
            if(userDevice==null){
                throw new IllegalStateException("UserDevice not found");
            }

            Map<String, String> data = new HashMap<>();
            data.put("senderId", dto.getSenderId());
            data.put("receiverId", dto.getReceiverId());
            data.put("type", dto.getType().name());

            if (dto.getPostId() != null) data.put("postId", dto.getPostId());
            if (dto.getCommentId() != null) data.put("commentId", dto.getCommentId());
            if (dto.getPageId() != null) data.put("pageId", dto.getPageId());
            System.out.println("User device "+ userDevice.getFcmToken());

            String token = userDevice.getFcmToken();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle("Bạn có một thông báo mới !")
                            .setBody(dto.getBody())
                            .build())
                    .setWebpushConfig(WebpushConfig.builder()
                            .setNotification(new WebpushNotification("Bạn có một thông báo mới !", dto.getBody()))
                            .putAllData(data)
                            .build())
                    .build();

            var response = FirebaseMessaging.getInstance().sendAsync(message);
            System.out.println("Gui thong bao thanh cong " + response);
            System.out.println("DTO: ," + dto.toString());
            this.createNotification(dto); //Create in database

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Loi trong viec send Notification " + e.getMessage());
        }
    }

    @Override
    public NotificationResponse createNotification(NotificationRequestDTO notificationRequestDTO) {

        User sender = userRepository.findById(notificationRequestDTO.getSenderId())
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));

        User receiver = userRepository.findById(notificationRequestDTO.getReceiverId())
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        vn.gt.__back_end_javaspring.entity.Notification notification = notificationMapper
                .toModel(notificationRequestDTO);

        notification.setSender(sender);
        notification.setReceiver(receiver);

        System.out.println("Notification sender "+notification.getSender().getFullName());
        System.out.println("Notification receiver "+notification.getReceiver().getFullName());

        vn.gt.__back_end_javaspring.entity.Notification saved = notificationRepository.save(notification);
        System.out.println("After saved");
        System.out.println("Notification sender "+notification.getSender().getFullName());
        System.out.println("Notification receiver "+notification.getReceiver().getFullName());
        return notificationMapper.toResponse(saved);
    }

    @Override
    public List<NotificationResponse> getNoticationByUserId(String userId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        List<vn.gt.__back_end_javaspring.entity.Notification> notifications = notificationRepository
                .findByReceiver_IdOrderByCreatedAtDesc(userId);

        return notificationMapper.toResponseList(notifications);
    }

    @Override
    public void deleteNotification(String notificationId) {
        vn.gt.__back_end_javaspring.entity.Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFound("Notification not found"));

        notificationRepository.delete(notification);

    }

    @Override
    public void markUnread(String notificationId) {
        vn.gt.__back_end_javaspring.entity.Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFound("Notification not found"));

        notification.setRead(false);
        notificationRepository.save(notification);
    }

    @Override
    public void markRead(String notificationId) {
        vn.gt.__back_end_javaspring.entity.Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFound("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAllRead(String userId) {
        User receiver = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        List<vn.gt.__back_end_javaspring.entity.Notification> notifications = notificationRepository
                .findByReceiver_IdOrderByCreatedAtDesc(userId);

        for (vn.gt.__back_end_javaspring.entity.Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

}
