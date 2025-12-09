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
import vn.gt.__back_end_javaspring.repository.FollowRepository;
import vn.gt.__back_end_javaspring.repository.NotificationRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.NotificationService;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PageRepository pageRepository;
    private final FollowRepository followRepository;
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

        if (!Objects.equals(notification.getReceiver().getId(), receiverId)) {
            throw new OnwerCanNotActionOnTheirs("You are not owner of this notification");
        }

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

    private Notification notificationHelper(User receiver, User actor, NotificationType type) {
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
        if (receiver == null) {
            return;
        }

        if (receiver.equals(actor)) {
            throw new OwnerCanNotLikePost("Owner can not like this post");
        }

        Notification notification = notificationHelper(receiver, actor, NotificationType.LIKE_POST);

        notification.setTitle(receiver.getFullName() + " liked your post");
        notification.setContent(blog.getCaption());
        notification.setRedirectUrl("/blogs/" + blog.getId());

        notificationRepository.save(notification);


    }

    @Override
    public void notifyCommentPost(User actor, Comment comment) {
        Blog blog = comment.getBlog();
        if (blog == null || blog.getUser() == null) {
            return;
        }

        User owner = blog.getUser();
        if (owner.equals(actor)) {
            throw new OnwerCanNotActionOnTheirs("Owner can not comment this post");
        }

        Notification notification = new Notification();
        notification = notificationHelper(owner, actor,  NotificationType.COMMENT_POST);

        notification.setTitle(actor.getFullName() + " comment your post");
        notification.setContent(comment.getContent());
        notification.setRedirectUrl("/blogs/" + blog.getId() );

        notificationRepository.save(notification);
    }

    @Override
    public void notifySharePost(User actor, Blog blog) {
        User owner = blog.getUser();
        if (owner == null) {
            return; // hoặc xử lý page owner nếu cần
        }
        if (owner.equals(actor)) {
            throw new OnwerCanNotActionOnTheirs("Owner can not share this post");
        }

        Notification notification = new Notification();
        notification = notificationHelper(owner, actor,  NotificationType.SHARE_POST);

        notification.setTitle(owner.getFullName() + "share your post");
        notification.setContent(blog.getCaption());
        notification.setRedirectUrl("/blogs/" + blog.getId());

        notificationRepository.save(notification);


    }

    @Override
    public void notifyReplyComment(User actor, Comment replyComment) {
        Comment parent = replyComment.getParentComment();
        if (parent == null || parent.getUser() == null) {
            return;
        }

        User owner = parent.getUser();
        if (owner.equals(actor)) {
            throw new OnwerCanNotActionOnTheirs("Owner can not reply their own comment (if you want forbid)");
        }

        Notification notification = notificationHelper(owner, actor, NotificationType.REPLY_COMMENT);
        notification.setTitle(actor.getFullName() + " replied to your comment");
        notification.setContent(replyComment.getContent());

        Blog blog = replyComment.getBlog();
        if (blog != null) {
            notification.setRedirectUrl("/blogs/" + blog.getId() + "#comment-" + replyComment.getId());
        } else {
            notification.setRedirectUrl("/comments/" + replyComment.getId());
        }

        notificationRepository.save(notification);

    }

    @Override
    public void notifyLikeComment(User actor, Comment comment) {
        User owner = comment.getUser();
        if (owner == null) {
            return;
        }
        if (owner.equals(actor)) {
            throw new OnwerCanNotActionOnTheirs("Owner can not like this comment");
        }
        Notification notification = new Notification();
        notification = notificationHelper(owner, actor,  NotificationType.LIKE_COMMENT);

        notification.setTitle(owner.getFullName() + "like your comment");
        notification.setContent(comment.getContent());
        notification.setRedirectUrl("/comments/" + comment.getId());

        notificationRepository.save(notification);

    }

    @Override
    public void notifyNewFollower(User follower, User followedUser) {
        if (followedUser.equals(follower)) {
            throw new OnwerCanNotActionOnTheirs("Follower can not follow themselves");
        }

        Notification notification = notificationHelper(followedUser, follower, NotificationType.NEW_FOLLOWER);
        notification.setTitle(follower.getFullName() + " just followed you");
        notification.setContent(follower.getFullName() + " has started following you.");
        notification.setRedirectUrl("/users/" + follower.getId());

        notificationRepository.save(notification);
    }

    @Override
    public void notifyNewPageFollower(User follower, vn.gt.__back_end_javaspring.entity.Page page) {
        if (page.getCafeOwner() == null || page.getCafeOwner().getUser() == null) {
            return;
        }
        User owner = page.getCafeOwner().getUser();


        // receiver = owner, actor = follower
        Notification notification = notificationHelper(owner, follower, NotificationType.NEW_PAGE_FOLLOWER);
        notification.setTitle(follower.getFullName() + " just followed your page");
        notification.setContent("Page: " + page.getPageName());
        notification.setRedirectUrl("/pages/" + page.getId());

        notificationRepository.save(notification);
    }

    @Override
    public void notifyPageNewPost(vn.gt.__back_end_javaspring.entity.Page page, Blog blog) {
        List<Follow> followers = followRepository.findByFollowedPage_Id(page.getId());
        for (Follow follow : followers) {
            User receiver = follow.getFollower();
            if (receiver == null) continue;

            Notification notification = notificationHelper(receiver, page.getCafeOwner().getUser(), NotificationType.PAGE_NEW_POST);
            notification.setTitle("Page " + page.getPageName() + " has a new post");
            notification.setContent(blog.getCaption());
            notification.setRedirectUrl("/pages/" + page.getId() + "/blogs/" + blog.getId());

            notificationRepository.save(notification);
        }


    }

}
