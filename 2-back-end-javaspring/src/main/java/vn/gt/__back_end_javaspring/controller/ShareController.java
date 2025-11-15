package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.ShareRequestDTO;
import vn.gt.__back_end_javaspring.DTO.ShareResponseDTO;
import vn.gt.__back_end_javaspring.service.ShareService;

@RestController
@RequestMapping("/api/shares")
@RequiredArgsConstructor
@Validated
public class ShareController {
    private final ShareService shareService;

    @PostMapping
    public ResponseEntity<ShareResponseDTO> createShare(@RequestBody @Valid ShareRequestDTO request) {
        return ResponseEntity.ok(shareService.createShare(request));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> hasShared(@RequestParam String userId, @RequestParam String blogId) { // <-- blogId
        return ResponseEntity.ok(shareService.hasShared(userId, blogId));
    }
}
