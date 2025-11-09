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

@Table(name = "share")
@Entity
@Data
public class Share {
	@Id
	@Column(name = "shareID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int shareID;

	@Column(name = "userID_1")
	private String userID_1;

	@Column(name = "userID_2")
	private String userID_2;

	@Column(name = "createAt")
	private Date createAt;

}