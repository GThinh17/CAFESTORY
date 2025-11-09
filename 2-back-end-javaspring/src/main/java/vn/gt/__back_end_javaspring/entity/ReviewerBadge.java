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
@Table(name = "reviewerBadge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewerBadge {
	@Id
	@Column(name = "reviewerBadgeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int reviewerBadgeID;

	@Column(name = "reviewerBagdeType")
	private String reviewerBadgeType;

	@Column(name = "status")
	boolean status;

	@Column(name = "createAt")
	Date createAt;

	@Column(name = "reviewerID")
	private String reviewerID;

	@Column(name = "updateAt")
	private String updateAt;

}