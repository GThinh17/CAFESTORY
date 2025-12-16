package vn.gt.__back_end_javaspring.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayOutDTO {

    private String reviewerId;

    private String walletId;

    private String walletTransactionId;

    private BigDecimal amount;

    private String currency;

    private String status;

    private String note;
}
