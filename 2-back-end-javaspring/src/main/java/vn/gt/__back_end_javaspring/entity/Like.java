package vn.gt.__back_end_javaspring.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "like")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
	@Id
	@Column(name = "like_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;

	@Column(name = "create_at")
	private LocalDateTime createAt;

}