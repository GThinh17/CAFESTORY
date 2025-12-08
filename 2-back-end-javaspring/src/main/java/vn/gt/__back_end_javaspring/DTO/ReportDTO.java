package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.ReportType;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {

    private String reportingUser; // thằng report

    @Enumerated(EnumType.STRING)
    // chatbot them
    private ReportType reportType;

    private String reported; // Thang bị report

    private String problem;

    private String description;

    // model tu themS
    private String Feedback;

    private Boolean isFlagged;

    private Boolean isBanned;

    private Boolean isDeleted;

}
