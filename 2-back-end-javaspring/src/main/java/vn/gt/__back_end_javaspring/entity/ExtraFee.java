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
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "extraFee")
@AllArgsConstructor
@NoArgsConstructor
public class ExtraFee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "extraFeeId", length = 36)
    private String extraFeeId;

    @Column(name = "extraFeeName")
    private String extraFeeName;

    @Column(name = "total")
    private Long total;

    @Column(name = "description")
    private String description;

    @Column(name = "expiredTime")
    private int expiredTime;

    @Column(name = "isDelete")
    private Boolean isDelete;

}
