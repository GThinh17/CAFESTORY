package vn.gt.__back_end_javaspring.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    String paymentId;

    Integer amount;

    PaymentStatus status;

    LocalDateTime processedAt;

    String productionId;

    String userId;

    Long total;

}
