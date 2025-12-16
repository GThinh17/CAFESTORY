package vn.gt.__back_end_javaspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.gt.__back_end_javaspring.entity.EarningSummary;

import java.util.List;
import java.util.Optional;

public interface EarningSummaryRepository extends JpaRepository<EarningSummary, String> {
    List<EarningSummary> findAllByReviewer_Id(String reviewerId);
    List<EarningSummary> findAllByMonth(Long month);
    List<EarningSummary> findAllByYear(Long year);
    Optional<EarningSummary> findByReviewer_IdAndYearAndMonth(String reviewerId, Integer year, Integer month);
    Boolean existsByReviewer_IdAndYearAndMonth(String reviewerId, Integer year, Integer month);
}
