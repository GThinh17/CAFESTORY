package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_method")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_method_id")
    private String id;

    @Column(name = "provider", length = 50)
    private String provider; // BANK, MOMO, PAYPAL...

    @Column(name = "account_name", length = 150)
    private String accountName;

    @Column(name = "account_number", length = 100)
    private String accountNumber;

    @Column(name = "is_default")
    private Boolean defaultMethod;

    @Column(name = "is_verified")
    private Boolean verified;
}
