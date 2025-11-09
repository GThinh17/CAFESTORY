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
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
	@Id
	@Column(name = "commentID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int commentID;

	@Column(name = "userID")
	private String userID;

	@Column(name = "blogID")
	private String blogID;

	@Column(name = "commentLike")
	private int commentLike;

	@Column(name = "commentContext")
	private String commentContext;

	@Column(name = "createAt")
	Date createAt;

}