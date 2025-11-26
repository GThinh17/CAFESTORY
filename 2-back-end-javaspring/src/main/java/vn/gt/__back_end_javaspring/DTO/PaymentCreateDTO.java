package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreateDTO {
    @NotBlank(message = "UserId is required")
    private String userId;
    @NotBlank(message = "paymentMethodId is required")
    private String paymentMethodId;
    @NotBlank(message = "payout is required")
    private String payoutId;
    private BigDecimal amount;
}
