package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.gt.__back_end_javaspring.enums.RoleType;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role { //Check
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "role_id")
	String id;

    @Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private RoleType roleName;

    @Column(name = "description")
    String description;

}
