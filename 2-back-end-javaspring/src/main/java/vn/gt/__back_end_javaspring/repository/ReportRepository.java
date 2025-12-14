package vn.gt.__back_end_javaspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.gt.__back_end_javaspring.entity.Report;

public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findAllByReportingUser_Id(String userId); // lấy report do user tạo

    List<Report> findAllByReportedUser_Id(String userId); // lấy report mà user bị report

    List<Report> findAllByReportedBlog_Id(String blogId); // blog bị report

    List<Report> findAllByReportedPage_Id(String pageId); // page bị report
}
