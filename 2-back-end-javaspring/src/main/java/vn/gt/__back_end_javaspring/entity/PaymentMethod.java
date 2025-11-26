package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "paymentMethod")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "paymentMethodId", length = 36)
    private String paymentMethodId;

    @Column(name = "paymentMethodName")
    private String paymentMethodName;

    @Column(name = "createAt")
    private Date createAt;

}