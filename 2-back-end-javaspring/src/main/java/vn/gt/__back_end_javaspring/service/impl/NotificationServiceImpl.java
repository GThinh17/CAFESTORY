package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.NotificationType;
import vn.gt.__back_end_javaspring.exception.NotificationNotFound;
import vn.gt.__back_end_javaspring.exception.OnwerCanNotActionOnTheirs;
import vn.gt.__back_end_javaspring.exception.OwnerCanNotLikePost;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.NotificationMapper;
import vn.gt.__back_end_javaspring.repository.NotificationRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.NotificationService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Page<NotificationResponse> getNotifications(String receiverId, boolean unreadOnly, Pageable pageable) {
        Page<Notification> page = unreadOnly
                ? notificationRepository.findByReceiver_IdAndReadIsFalseOrderByCreatedAtDesc(receiverId, pageable)
                : notificationRepository.findByReceiver_IdOrderByCreatedAtDesc(receiverId, pageable);
        return page.map(notificationMapper::toResponse);
    }

    @Override
    public void markAsRead(String receiverId, String notificationId) {
        User user = userRepository.findById(receiverId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(()->new NotificationNotFound("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(String receiverId) {
        User user  = userRepository.findById(receiverId)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        List<Notification> notifications = notificationRepository.findByReceiver_IdAndReadIsFalse(receiverId);
        for (Notification notification : notifications) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    @Override
    public long countUnread(String receiverId) {
        User user = userRepository.findById(receiverId)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        Long count = notificationRepository.countByReceiver_IdAndReadIsFalse(receiverId);
        return count;
    }

    private Notification notificationHelper (User receiver, User actor, NotificationType type){
         Notification notification = new Notification();
         notification.setReceiver(receiver);
         notification.setActor(actor);
         notification.setType(type);
         notification.setRead(false);
         return notification;
    }

    @Override
    public void notifyLikePost(User actor, Blog blog) {
        User receiver = blog.getUser();
        if(receiver.equals(actor)) {
            throw new OwnerCanNotLikePost("Owner can not like this post");
        }

        Notification notification = new Notification();
        notification = notificationHelper(receiver, actor,  NotificationType.LIKE_POST);

        notification.setContent(receiver.getFullName() + "like your post");
        notification.setContent(blog.getCaption());
        notification.setRedirectUrl("/blogs/" + blog.getId());

        notificationRepository.save(notification);


    }

    @Override
    public void notifyCommentPost(User actor, Comment comment) {
        Blog blog = comment.getBlog();
        if(blog == null) {
            return;
        }

        User owner = blog.getUser();

        if(owner.equals(actor)) {
            throw new OnwerCanNotActionOnTheirs("Owner can not comment this post");
        }

        Notification notification = new Notification();
        notificationHelper(owner, actor, NotificationType.COMMENT_POST);

    }

    @Override
    public void notifySharePost(User actor, Blog blog) {

    }

    @Override
    public void notifyReplyComment(User actor, Comment replyComment) {

    }

    @Override
    public void notifyLikeComment(User actor, Comment comment) {

    }

    @Override
    public void notifyNewFollower(User follower, User followedUser) {

    }

    @Override
    public void notifyNewPageFollower(User follower, Page page) {

    }

    @Override
    public void notifyPageNewPost(Page page, Blog blog) {

    }

    @Override
    public void notifyWalletTransaction(WalletTransaction transaction) {

    }

    @Override
    public void notifyBadgeGranted(User user, Badge badge) {

    }

    @Override
    public void notifyConnectionSuggestion(User receiver, User suggestedUser, String reason) {

    }
}
