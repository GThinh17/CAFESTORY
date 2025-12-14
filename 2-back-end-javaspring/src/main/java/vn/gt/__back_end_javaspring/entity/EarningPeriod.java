package vn.gt.__back_end_javaspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "earning_period")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarningPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "period_id")
    private String id;

    @Column(name = "period_type", length = 50)
    private String periodType; // WEEKLY / MONTHLY / CUSTOM...

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status", length = 50)
    private String status; // OPEN / CLOSED / PAID...

}
