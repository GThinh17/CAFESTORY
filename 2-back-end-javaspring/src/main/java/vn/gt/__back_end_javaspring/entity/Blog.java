package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Blog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Blog {
	@Id
	@Column(name = "logID")
	String blogID;

	@Column(name = "blogContext")
	private String blogContext;

	@Column(name = "blogImage")
	private String blogImage;

	@Column(name = "blogLike")
	private int blogLike;

	@Column(name = "blogShare")
	private int blogShare;

	@Column(name = "status")
	private boolean status;

	@Column(name = "createAt")
	private Date createAt;

	@Column(name = "owner")
	private String owner;

}