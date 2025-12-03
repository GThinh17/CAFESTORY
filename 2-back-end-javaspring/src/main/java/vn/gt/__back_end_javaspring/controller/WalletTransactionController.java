package vn.gt.__back_end_javaspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionResponse;
import vn.gt.__back_end_javaspring.enums.TransactionStatus;
import vn.gt.__back_end_javaspring.enums.TransactionType;
import vn.gt.__back_end_javaspring.service.WalletTransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/wallet-transactions")
@RequiredArgsConstructor
public class WalletTransactionController {

    private final WalletTransactionService walletTransactionService;

    @PostMapping("")
    public ResponseEntity<WalletTransactionResponse> createTransaction(
            @Valid @RequestBody WalletTransactionCreateDTO dto
    ) {
        WalletTransactionResponse data = walletTransactionService.create(dto);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletTransactionResponse> getTransactionById(
            @PathVariable String id
    ) {
        WalletTransactionResponse data = walletTransactionService.getById(id);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/by-wallet/{walletId}")
    public ResponseEntity<List<WalletTransactionResponse>> getTransactionsByWalletId(
            @PathVariable String walletId
    ) {
        List<WalletTransactionResponse> data = walletTransactionService.getByWalletId(walletId);
        return ResponseEntity.ok().body(data);
    }

//    public ResponseEntity<List<WalletTransactionResponse>> getTransactionsByUserId(
//            @PathVariable String userId
//    ) {
//        List<WalletTransactionResponse> data = walletTransactionService.getByUserId(userId);
//        return ResponseEntity.ok().body(data);
//    }

    @GetMapping("/by-wallet/{walletId}/type")
    public ResponseEntity<List<WalletTransactionResponse>> getTransactionsByWalletIdAndType(
            @PathVariable String walletId,
            @RequestParam TransactionType type
    ) {
        List<WalletTransactionResponse> data =
                walletTransactionService.getByWalletIdAndType(walletId, type);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/by-wallet/{walletId}/status")
    public ResponseEntity<List<WalletTransactionResponse>> getTransactionsByWalletIdAndStatus(
            @PathVariable String walletId,
            @RequestParam TransactionStatus status
    ) {
        List<WalletTransactionResponse> data =
                walletTransactionService.getByWalletIdAndStatus(walletId, status);
        return ResponseEntity.ok().body(data);
    }
}
