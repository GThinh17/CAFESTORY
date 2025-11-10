package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	@Id
	
	@Column(name = "roleID")
	String roleID;

	@Column(name = "userID")
	String userID;

	@Column(name = "roleName")
	String roleName;

}
