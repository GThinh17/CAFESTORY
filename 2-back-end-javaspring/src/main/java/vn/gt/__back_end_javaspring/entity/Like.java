package vn.gt.__back_end_javaspring.entity;


import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.LikeTargetType;

@Entity
@Table(name = "likes",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_id", "target_type"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id",  nullable = false)
	private User user; //Nguoi like

    @Column(name = "target_id", nullable = false)
    private String targetId; //Receiver

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private LikeTargetType targetType;

	@Column(name = "createAt")
	private LocalDateTime createAt;

    @PrePersist
    protected  void onCreate(){
        createAt = LocalDateTime.now();
    }

}