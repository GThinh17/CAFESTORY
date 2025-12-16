package vn.gt.__back_end_javaspring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.ReportType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private String reportId;

    private ReportType reportType;

    private User reportedUser; // Thang bị report

    private Blog reportedBlog; // Thang bị report

    private Page reportedPage;

    private String problem;

    private String description;

    private String feedback;
}
