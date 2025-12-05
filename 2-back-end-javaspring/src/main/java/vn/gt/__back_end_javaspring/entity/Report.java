package vn.gt.__back_end_javaspring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.ReportStatus;
import vn.gt.__back_end_javaspring.enums.ReportType;

@Entity(name = "report")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporting_user_id")
    private User reportingUser; // thằng report

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id")
    private User reportedUser; // Thang bị report

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_blog_id")
    private User reportedBlog; // Thang bị report

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_page_id")
    private Page reportedPage;

    @Column(name = "problem")
    private String problem;

    @Column(name = "description")
    private String description;

    @Column(name = "report_status")
    private ReportStatus report_status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "handled_at")
    private LocalDateTime handledAt;

    @Column(name = "feedback")
    private String Feedback;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
