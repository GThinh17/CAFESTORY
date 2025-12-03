package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import vn.gt.__back_end_javaspring.enums.ReviewerStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviewer")
@NoArgsConstructor
@AllArgsConstructor
@Data

@PrimaryKeyJoinColumn(name = "user_id")
public class Reviewer extends User {

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "follower_count")
    private Integer followerCount;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "income", precision = 15, scale = 2)
    private BigDecimal income;

    @Column(name = "join_at")
    private LocalDateTime joinAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReviewerStatus status;

}
