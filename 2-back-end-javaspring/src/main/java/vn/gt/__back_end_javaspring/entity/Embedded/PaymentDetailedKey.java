package vn.gt.__back_end_javaspring.entity.Embedded;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailedKey implements Serializable {

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "extra_fee_id", length = 36)
    private String extraFeeId;

    @Column(name = "payment_id", length = 36)
    private String paymentId;

    @Column(name = "payment_method_id", length = 36)
    private String paymentMethodId;
}
