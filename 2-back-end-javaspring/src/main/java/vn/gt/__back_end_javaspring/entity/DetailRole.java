package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//
//public class DetailRoleId implements Serializable {
//    @Column(name = "role_id", nullable = false)
//    private Long roleId;
//
//    @Column(name = "user_id", nullable = false)
//    private Long userId;
//}


@Entity
@Table(name = "detail_roles")
@Data
public class DetailRole{
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}