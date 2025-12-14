package vn.gt.__back_end_javaspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getNotifications(
            @RequestParam String receiverId,
            @RequestParam(defaultValue = "false") boolean unreadOnly,
            Pageable pageable
    ) {
        Page<NotificationResponse> data =
                notificationService.getNotifications(receiverId, unreadOnly, pageable);
        return ResponseEntity.ok(data);
    }


    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable String notificationId,
            @RequestParam String receiverId
    ) {
        notificationService.markAsRead(receiverId, notificationId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @RequestParam String receiverId
    ) {
        notificationService.markAllAsRead(receiverId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/unread-count")
    public ResponseEntity<Long> countUnread(
            @RequestParam String receiverId
    ) {
        long count = notificationService.countUnread(receiverId);
        return ResponseEntity.ok(count);
    }
}
