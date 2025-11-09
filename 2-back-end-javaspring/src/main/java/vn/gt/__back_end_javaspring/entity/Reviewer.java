package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviewer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reviewer {
	@Id
	@Column(name = "reviewerID")
	String reviewerID;

	@Column(name = "userID")
	String userID;

	@Column(name = "reviewerName")
	String reviewerName;

}
