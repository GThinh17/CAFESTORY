package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
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

    private User reportingUser; // thằng report

    private ReportType reportType;

    private User reportedUser; // Thang bị report

    private User reportedBlog; // Thang bị report

    private Page reportedPage;

    private String problem;

    private String description;

}
