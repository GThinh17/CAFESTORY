package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.EarningSummaryCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningSummaryResponse;
import vn.gt.__back_end_javaspring.enums.EarningSummaryStatus;

import java.util.List;

public interface EarningSummaryService {
    EarningSummaryResponse createSummary(EarningSummaryCreateDTO earningSummaryCreateDTO);
    EarningSummaryResponse updateStatusSummary(String earningSummaryId, String status);
    List<EarningSummaryResponse> getSummaryByUserId(String userId);
    EarningSummaryResponse getSummaryByEarningSummaryId(String earningSummaryId);
    List<EarningSummaryResponse> getSummaryByMonth(Long month);
    List<EarningSummaryResponse> getSummaryByYear(Long year);
}
