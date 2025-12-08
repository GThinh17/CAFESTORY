package vn.gt.__back_end_javaspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.storage.Acl.Entity;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.ReportDTO;
import vn.gt.__back_end_javaspring.entity.Report;
import vn.gt.__back_end_javaspring.enums.ReportType;
import vn.gt.__back_end_javaspring.service.ReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("")
    public ResponseEntity<?> GetAllReport() {
        return ResponseEntity.ok().body(this.reportService.GetAllReport());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetReportById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(this.reportService.GetReportById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> CreateReport(@RequestBody ReportDTO report) {
        // System.out.println(">>>REPORT<<<<" + report);
        this.reportService.CreateReport(report);
        return ResponseEntity.ok().body(report);
    }

    // @PutMapping("/{id}")
    // public ResponseEntity<?> putMethodName(@PathVariable("id") String id,
    // @RequestBody ReportDTO report) {
    // Report createReport = new Report();
    // createReport.setDescription(report.getDescription());
    // createReport.setProblem(report.getProblem());
    // createReport.setReportingUser(report.getReportingUser());
    // ReportType roleType = report.getReportType();
    // if (roleType == ReportType.BLOG) {
    // createReport.setReportedBlog(report.getReportedBlog());
    // } else if (roleType == ReportType.PAGE) {
    // createReport.setReportedPage(report.getReportedPage());
    // } else {
    // createReport.setReportedUser(report.getReportedUser());
    // }
    // return ResponseEntity.ok().body(this.reportService.UpdateReportById(id,
    // createReport));
    // }
    @PatchMapping("/{id}")
    public ResponseEntity<?> UpdateReportByModel(@PathVariable("id") String id, @RequestBody ReportDTO reportDTO) {
        Report UpdateReport = this.reportService.UpdateReportByIdFromChatBot(id, reportDTO);

        return ResponseEntity.ok().body(reportDTO);
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<?> GetListReportByUserId(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(this.reportService.GetListReportByUserId(id));
    }

    @GetMapping("/blogId/{id}")
    public ResponseEntity<?> GetListReportByBlogId(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(this.reportService.GetListReportByBlogId(id));
    }

    @GetMapping("/pageId/{id}")
    public ResponseEntity<?> GetListReportByPageId(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(this.reportService.GetListReportByPageId(id));
    }
}
