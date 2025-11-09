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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paymentMethodID")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentMethod {
	@Id
	@Column(name = "payemntMethodID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long notifocationID;

	@Column(name = "userID")
	private String userID;

	@Column(name = "payemntMethodName")
	private int payemntMethodName;

	@Column(name = "status")
	private boolean status;

	@Column(name = "createAt")
	private Date createAt;

}