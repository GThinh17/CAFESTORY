package vn.gt.__back_end_javaspring.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayOutResponse {
    private String id;
    private String reviewerId;

    private String walletId;

    private String userId;

    private String walletTransactionId;

    private String balancedBefore;

    private String balancedAfter;

    private String walletTransactionStatus;

    private BigDecimal amount;

    private String currency;

    private String status;

    private String note;
}
