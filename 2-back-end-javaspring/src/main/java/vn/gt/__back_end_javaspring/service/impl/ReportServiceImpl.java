package vn.gt.__back_end_javaspring.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.ReportDTO;
import vn.gt.__back_end_javaspring.DTO.ReportResponseDTO;
import vn.gt.__back_end_javaspring.entity.Blog;
import vn.gt.__back_end_javaspring.entity.Page;
import vn.gt.__back_end_javaspring.entity.Report;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.enums.ReportType;
import vn.gt.__back_end_javaspring.exception.BadgeNotFound;
import vn.gt.__back_end_javaspring.exception.BlogNotFoundException;
import vn.gt.__back_end_javaspring.exception.PageNotFoundException;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.ReportMapper;
import vn.gt.__back_end_javaspring.repository.BlogRepository;
import vn.gt.__back_end_javaspring.repository.PageRepository;
import vn.gt.__back_end_javaspring.repository.ReportRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.service.BlogService;
import vn.gt.__back_end_javaspring.service.PageService;
import vn.gt.__back_end_javaspring.service.ReportService;
import vn.gt.__back_end_javaspring.service.UserService;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PageRepository pageRepository;
    private final ReportMapper reportMapper;

    @Override
    public List<ReportResponseDTO> GetAllReport() {
        return reportMapper.toDTOList(reportRepository.findAll());
    }

    @Override
    public Optional<?> GetReportById(String reportId) {
        return this.reportRepository.findById(reportId);
    }

    @Override
    public Report CreateReport(ReportDTO report) {
        Report createReport = new Report();
        createReport.setDescription(report.getDescription());
        createReport.setProblem(report.getProblem());

        User ReportingUser = this.userRepository.findById(report.getReportingUser())
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        createReport.setReportingUser(ReportingUser);

        ReportType roleType = report.getReportType();
        createReport.setReportType(roleType);
        if (roleType == ReportType.BLOG) {

            Blog blog = this.blogRepository.findById(report.getReported())
                    .orElseThrow(() -> new BlogNotFoundException("Blog not found"));

            createReport.setReportedBlog(blog);

        } else if (roleType == ReportType.PAGE) {

            Page page = this.pageRepository.findById(report.getReported())
                    .orElseThrow(() -> new PageNotFoundException("Page not found"));

            createReport.setReportedPage(page);

        } else if (roleType == ReportType.USER) {

            User ReportedUser = this.userRepository.findById(report.getReported())

                    .orElseThrow(() -> new UserNotFoundException("user not found"));
            createReport.setReportedUser(ReportedUser);
        }

        return this.reportRepository.save(createReport);

    }

    @Override
    public Report UpdateReportById(String reportId, Report report) {
        Report updateReport = this.reportRepository.findById(reportId).orElseThrow(null);
        updateReport = report;
        return this.reportRepository.save(updateReport);

    }

    @Override
    public List<ReportResponseDTO> GetListReportByUserId(String userId) {
        return reportMapper.toDTOList(reportRepository.findAllByReportedUser_Id(userId));
    }

    @Override
    public List<ReportResponseDTO> GetListReportByBlogId(String blogId) {
        return reportMapper.toDTOList(reportRepository.findAllByReportedBlog_Id(blogId));
    }

    @Override
    public List<ReportResponseDTO> GetListReportByPageId(String pageId) {
        return reportMapper.toDTOList(reportRepository.findAllByReportedPage_Id(pageId));
    }

    @Override
    public ReportResponseDTO UpdateReportByIdFromChatBot(String reportId, ReportDTO reportDTO) {

        Report updateReport = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        updateReport.setFeedback(reportDTO.getFeedback());
        updateReport.setIsBanned(reportDTO.getIsBanned());
        updateReport.setIsDeleted(reportDTO.getIsDeleted());
        updateReport.setIsFlagged(reportDTO.getIsFlagged());
        updateReport.setHandledAt(LocalDateTime.now());

        Report saved = reportRepository.save(updateReport);

        return reportMapper.toDTO(saved);
    }

}
