package vn.gt.__back_end_javaspring.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDTO {
    private String id;
    private String reportingUserId;
    private String reportedUserId;
    private String reportType;
    private String problem;
    private String description;
    private LocalDateTime createdAt;
}