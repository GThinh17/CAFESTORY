package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "likes")
@Data
public class Like {
	@Id
	@Column(name = "likeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int likeID;

	@Column(name = "userID_1")
	private String userID_1;

	@Column(name = "userID_2")
	private String userID_2; // reciever

	@Column(name = "createAt")
	private Date createAt;

}