package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.EarningPeriodType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "earning_period")
public class EarningPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "earning_period_id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type", nullable = false, length = 20)
    private EarningPeriodType periodType;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "quarter")
    private Integer quarter; // nullable

    @Column(name = "month")
    private Integer month; // nullable

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
