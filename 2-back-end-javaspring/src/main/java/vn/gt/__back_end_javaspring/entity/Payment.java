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
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
	@Id
	@Column(name = "paymentID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long paymentID;

	@Column(name = "extraFeeID")
	Long extraFeeID;

	@Column(name = "userID")
	Long userID;

	@Column(name = "createAt")
	Date createAt;

	@Column(name = "amount")
	Long amount;

	@Column(name = "expired")
	Date expired;
}
