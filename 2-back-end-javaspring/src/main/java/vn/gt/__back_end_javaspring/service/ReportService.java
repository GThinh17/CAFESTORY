package vn.gt.__back_end_javaspring.service;

import java.util.List;
import java.util.Optional;

import vn.gt.__back_end_javaspring.DTO.ReportDTO;
import vn.gt.__back_end_javaspring.entity.Report;

public interface ReportService {
    public List<?> GetAllReport();

    public Optional<?> GetReportById(String reportId);

    public Report CreateReport(ReportDTO report);

    public Report UpdateReportById(String reportId, Report report);

    public List<?> GetListReportByUserId(String userId);

    public List<?> GetListReportByBlogId(String blogId);

    public List<?> GetListReportByPageId(String pageId);

}
