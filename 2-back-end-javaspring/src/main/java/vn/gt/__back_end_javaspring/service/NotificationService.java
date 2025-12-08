package vn.gt.__back_end_javaspring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.entity.*;

public interface NotificationService {

    Page<NotificationResponse> getNotifications(String receiverId, boolean unreadOnly, Pageable pageable);

    void markAsRead(String receiverId, String notificationId);

    void markAllAsRead(String receiverId);

    long countUnread(String receiverId);

    // Tạo notify cho các hành động:

    void notifyLikePost(User actor, Blog blog);

    void notifyCommentPost(User actor, Comment comment);

    void notifySharePost(User actor, Blog blog);

    void notifyReplyComment(User actor, Comment replyComment);

    void notifyLikeComment(User actor, Comment comment);

    void notifyNewFollower(User follower, User followedUser);

    void notifyNewPageFollower(User follower, Page page);

    void notifyPageNewPost(Page page, Blog blog);

    void notifyWalletTransaction(WalletTransaction transaction);

    void notifyBadgeGranted(User user, Badge badge);

    // Optional: notify suggestion
    void notifyConnectionSuggestion(User receiver, User suggestedUser, String reason);
}
