package vn.gt.__back_end_javaspring.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ReportResponseDTO {

    private String id;
    private String reportingUserId;
    private String reportedUserId;
    private String reportedBlogId;
    private String reportedPageId;
    private String reportType;
    private String problem;
    private String description;
    private LocalDateTime createdAt;
    // model tu themS
    private String Feedback;

    private Boolean isFlagged;

    private Boolean isBanned;

    private Boolean isDeleted;
}