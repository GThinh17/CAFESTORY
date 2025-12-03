package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Wallet;
import vn.gt.__back_end_javaspring.enums.TransactionStatus;
import vn.gt.__back_end_javaspring.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletTransactionResponse {
    private String walletTransactionId;

    private String walletId;

    private BigDecimal walletBalance;

    private String walletUserId;

    private BigDecimal amount;

    private String type;

    private String status;

    private BigDecimal balanceAfter;

    private BigDecimal balanceBefore;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
