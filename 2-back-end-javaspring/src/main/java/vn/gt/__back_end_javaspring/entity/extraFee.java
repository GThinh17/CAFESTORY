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
@Table(name = "extraFee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class extraFee {
	@Id
	@Column(name = "extraFeeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long extraFeeID;

	@Column(name = "userID")
	String userID;

	@Column(name = "total")
	Long total;

	@Column(name = "description")
	String description;

	@Column(name = "payment_method")
	String payment_method;

	@Column(name = "payment_status")
	String payment_status;

	@Column(name = "expiredTime")
	Date expiredTime;
}
