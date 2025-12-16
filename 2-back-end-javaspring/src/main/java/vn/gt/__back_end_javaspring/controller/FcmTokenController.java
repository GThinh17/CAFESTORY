package vn.gt.__back_end_javaspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.FcmTokenRequestDTO;
import vn.gt.__back_end_javaspring.service.UserDeviceService;
import vn.gt.__back_end_javaspring.service.UserService;

@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FcmTokenController {
    private  final UserDeviceService userDeviceService;

    @PostMapping("/token")
    public ResponseEntity<String> fcmToken(
            @RequestBody FcmTokenRequestDTO fcmTokenRequestDTO,
            @RequestHeader("X-USER-ID") String userId
            ) {
        System.out.println("FcmTokenController fcmTokenRequestDTO = " + fcmTokenRequestDTO);
        userDeviceService.saveOrUpdate(userId, fcmTokenRequestDTO);
        return ResponseEntity.ok("Adding succesfully");
    }

    public record DeleteFcmTokenRequest(String fcmToken) {}

    @DeleteMapping("/token")
    public ResponseEntity<?> removeToken(
            @RequestBody DeleteFcmTokenRequest req
    ) {
        System.out.println("FcmTokenController req = " + req);
        userDeviceService.delete(req.fcmToken());
        return ResponseEntity.ok().build();
    }


}
