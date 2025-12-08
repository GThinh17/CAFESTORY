package vn.gt.__back_end_javaspring.enums;

public enum ReportStatus {
    PENDING, // Chưa xử lý
    REVIEWING, // Admin đang xem xét
    APPROVED, // Báo cáo đúng -> xử lý
    REJECTED // Báo cáo sai
}
