package vn.gt.__back_end_javaspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.DTO.EarningSummaryCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningSummaryResponse;
import vn.gt.__back_end_javaspring.enums.EarningSummaryStatus;
import vn.gt.__back_end_javaspring.service.EarningSummaryScheduler;
import vn.gt.__back_end_javaspring.service.EarningSummaryService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/earning-summary")
@RequiredArgsConstructor
public class EarningSummaryController {

    private final EarningSummaryService earningSummaryService;
    private final EarningSummaryScheduler earningSummaryScheduler;

    @PostMapping
    public ResponseEntity<EarningSummaryResponse> createSummary(@RequestBody EarningSummaryCreateDTO dto) {
        EarningSummaryResponse response = earningSummaryService.createSummary(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/run")
    public ResponseEntity<?> runMonthlySummary() {
        earningSummaryScheduler.generateMonthlySummary();
        return ResponseEntity.ok(
                "Generate monthly summary triggered at " + LocalDateTime.now()
        );
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<EarningSummaryResponse> updateStatus(
            @PathVariable("id") String earningSummaryId,
            @RequestBody String status
    ) {

        EarningSummaryResponse response = earningSummaryService.updateStatusSummary(earningSummaryId, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EarningSummaryResponse>> getSummaryByUser(@PathVariable String userId) {
        List<EarningSummaryResponse> responses = earningSummaryService.getSummaryByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EarningSummaryResponse> getSummaryById(@PathVariable("id") String earningSummaryId) {
        EarningSummaryResponse response = earningSummaryService.getSummaryByEarningSummaryId(earningSummaryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<List<EarningSummaryResponse>> getSummaryByMonth(@PathVariable Integer month) {
        List<EarningSummaryResponse> responses = earningSummaryService.getSummaryByMonth(month.longValue());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<EarningSummaryResponse>> getSummaryByYear(@PathVariable Long year) {
        List<EarningSummaryResponse> responses = earningSummaryService.getSummaryByYear(year);
        return ResponseEntity.ok(responses);
    }

}
