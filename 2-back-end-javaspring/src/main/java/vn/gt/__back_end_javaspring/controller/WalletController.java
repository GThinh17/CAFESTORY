package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.WalletCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletResponse;
import vn.gt.__back_end_javaspring.DTO.WalletUpdateDTO;
import vn.gt.__back_end_javaspring.service.WalletService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("")
    public ResponseEntity<WalletResponse> createWallet(
            @Valid @RequestBody WalletCreateDTO dto) {

        WalletResponse data = walletService.createWallet(dto);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> getWalletById(
            @PathVariable String id) {

        WalletResponse data = walletService.findById(id);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WalletResponse> updateWallet(
            @PathVariable String id,
            @Valid @RequestBody WalletUpdateDTO walletUpdateDTO) {

        WalletResponse data = walletService.updateWallet(walletUpdateDTO, id);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWallet(@PathVariable String id) {
        walletService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restoreWallet(@PathVariable String id) {
        walletService.restore(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByUserId(@RequestParam String userId) {
        boolean exists = walletService.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<WalletResponse> getWalletByUserId(
            @PathVariable String userId) {

        WalletResponse data = walletService.findByUserId(userId);
        return ResponseEntity.ok(data);
    }

    // @PostMapping("/{id}/deposit")
    // public ResponseEntity<WalletResponse> deposit(
    // @PathVariable String id,
    // @RequestParam BigDecimal amount) {
    //
    // WalletResponse data = walletService.deposit(id, amount);
    // return ResponseEntity.ok(data);
    // }
    //
    // @PostMapping("/{id}/withdraw")
    // public ResponseEntity<WalletResponse> withdraw(
    // @PathVariable String id,
    // @RequestParam BigDecimal amount) {
    //
    // WalletResponse data = walletService.withdraw(id, amount);
    // return ResponseEntity.ok(data);
    // }
}
