package vn.gt.__back_end_javaspring.service;

import com.stripe.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.entity.EarningSummary;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarningSummaryScheduler {
    private final EarningSummaryService earningSummaryService;
    private final ReviewerService reviewerService;

//@Scheduled(fixedRate = 60000) // 2 phút
    @Scheduled(cron = "0 59 23 L * ?")
    public void generateMonthlySummary() {
    System.out.println("Job chạy lúc: " + LocalDateTime.now());

        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        int year = lastMonth.getYear();
        int month = lastMonth.getMonthValue();

        List<String> allReviewerIds = reviewerService.getAllReviewerIds();
        for(String reviewerId : allReviewerIds) {
            earningSummaryService.generateMonthlySummary(reviewerId, year, month);
        }
    }
}
