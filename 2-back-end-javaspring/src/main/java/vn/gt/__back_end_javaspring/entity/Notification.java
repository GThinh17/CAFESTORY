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
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification {
	@Id
	@Column(name = "notificationID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationID;

	@Column(name = "userID")
	private String userID;

	@Column(name = "notificationType")
	private int notificationType;

	@Column(name = "notificationContext")
	private String notificationContext;

	@Column(name = "createAt")
	private Date createAt;

}