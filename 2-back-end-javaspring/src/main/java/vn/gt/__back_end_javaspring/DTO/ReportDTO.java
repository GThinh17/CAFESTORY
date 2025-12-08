package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.ReportType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String reportingUser; // thằng report

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    private String reported; // Thang bị report

    private String problem;

    private String description;

}
