package vn.gt.__back_end_javaspring.entity;

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
@Table(name = "page")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Page {
	@Id
	@Column(name = "pageID")
	String pageID;

	@Column(name = "userid")
	String userID;

	@Column(name = "pageName")
	String pageName;

	@Column(name = "pageAdress")
	String pageAdress;

	@Column(name = "pageRegion")
	String pageRegion;

	@Column(name = "pageLike")
	String pageLike;

	@Column(name = "pageFollower")
	String pageFollower;

	@Column(name = "status")
	boolean status;

}
