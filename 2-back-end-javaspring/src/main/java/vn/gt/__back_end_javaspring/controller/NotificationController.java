package vn.gt.__back_end_javaspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;
import vn.gt.__back_end_javaspring.DTO.NotificationResponse;
import vn.gt.__back_end_javaspring.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getMyNotifications(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(
                notificationService.getNoticationByUserId(userId)
        );
    }


    @PostMapping("/send/{receiverId}")
    public ResponseEntity<Void> sendNotification(
            @PathVariable String receiverId,
            @RequestBody NotificationRequestDTO dto
    ) {
        notificationService.sendNotification(receiverId, dto);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markRead(
            @PathVariable String notificationId
    ) {
        notificationService.markRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{notificationId}/unread")
    public ResponseEntity<Void> markUnread(
            @PathVariable String notificationId
    ) {
        notificationService.markUnread(notificationId);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllRead(
            @RequestParam String userId
    ) {
        notificationService.markAllRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(
            @PathVariable String notificationId
    ) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
