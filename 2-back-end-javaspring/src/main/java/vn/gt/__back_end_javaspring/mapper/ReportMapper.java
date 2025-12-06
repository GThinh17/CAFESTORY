package vn.gt.__back_end_javaspring.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.gt.__back_end_javaspring.DTO.ReportResponseDTO;
import vn.gt.__back_end_javaspring.entity.Report;

@Component
public class ReportMapper {

    public ReportResponseDTO toDTO(Report report) {
        return new ReportResponseDTO(
                report.getId(),
                report.getReportingUser() != null ? report.getReportingUser().getId() : null,
                report.getReportedUser() != null ? report.getReportedUser().getId() : null,
                report.getReportType() != null ? report.getReportType().name() : null,
                report.getProblem(),
                report.getDescription(),
                report.getCreatedAt());
    }

    public List<ReportResponseDTO> toDTOList(List<Report> reports) {
        return reports.stream().map(this::toDTO).toList();
    }
}
