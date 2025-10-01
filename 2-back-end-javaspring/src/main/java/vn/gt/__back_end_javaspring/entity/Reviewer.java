package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Table(name = "reviewers")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "like", nullable = false)
    private Long like;

    @Column(name = "dislike", nullable = false)
    private Long dislike;

    @Column(name = "follower", nullable = false)
    private Long follower;
}
