package vn.gt.__back_end_javaspring.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import vn.gt.__back_end_javaspring.DTO.EarningSummaryCreateDTO;
import vn.gt.__back_end_javaspring.DTO.EarningSummaryResponse;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.EarningSummaryStatus;
import vn.gt.__back_end_javaspring.enums.SourceType;
import vn.gt.__back_end_javaspring.enums.TransactionType;
import vn.gt.__back_end_javaspring.exception.ReviewerNotFound;
import vn.gt.__back_end_javaspring.exception.UserNotFoundException;
import vn.gt.__back_end_javaspring.mapper.EarningSummaryMapper;
import vn.gt.__back_end_javaspring.repository.*;
import vn.gt.__back_end_javaspring.service.EarningSummaryService;
import vn.gt.__back_end_javaspring.service.ReviewerService;
import vn.gt.__back_end_javaspring.service.WalletService;
import vn.gt.__back_end_javaspring.service.WalletTransactionService;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EarningSummaryServiceImpl implements EarningSummaryService {
    private final EarningSummaryRepository earningSummaryRepository;
    private final FollowRuleRepository followRuleRepository;
    private final ReviewerRepository reviewerRepository;
    private final EarningSummaryMapper earningSummaryMapper;
    private final UserRepository userRepository;
    private final ReviewerService reviewerService;
    private final EarningEventRepository earningEventRepository;
    private final WalletService walletService;
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    @Override
    public EarningSummaryResponse createSummary(EarningSummaryCreateDTO dto) {
        Reviewer reviewer = reviewerRepository.findById(dto.getReviewerId())
                .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

        FollowRule followRule = followRuleRepository.findMatchingRule(dto.getTotalFollowerCount())
                .orElseThrow(()-> new RuntimeException("Follow Rule Not found"));

        EarningSummary summary = earningSummaryMapper.toModel(dto);

        summary.setReviewer(reviewer);
        summary.setFollowRule(followRule);
        //Set total Amount them follow Rule

        BigDecimal bonusAmount = followRule.getBonusAmount();
        BigDecimal totalEarningAmount = bonusAmount.add(dto.getTotalEarningAmount());

        summary.setBonusAmount(bonusAmount);
        summary.setTotalEarningAmount(totalEarningAmount);

        EarningSummary saved = earningSummaryRepository.save(summary);

        return earningSummaryMapper.toResponse(saved);

    }

    @Override
    public EarningSummaryResponse updateStatusSummary(String earningSummaryId, String status) {
        EarningSummary earningSummary = earningSummaryRepository.findById(earningSummaryId)
                .orElseThrow(()-> new RuntimeException("EarningSummary not found"));

        EarningSummaryStatus summaryStatus = EarningSummaryStatus.valueOf(status);

        earningSummary.setStatus(summaryStatus);
        earningSummaryRepository.save(earningSummary);
        return earningSummaryMapper.toResponse(earningSummary);
    }

    @Override
    public List<EarningSummaryResponse> getSummaryByUserId(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        Reviewer reviewer = null;
        if(reviewerService.isReviewerByUserId(userId)) {
             reviewer = reviewerRepository.findByUser_Id(userId);
            if(reviewer == null) {
                throw new ReviewerNotFound("Reviewer not found");
            }
        }
        String reviewerId = reviewer.getId();
        List<EarningSummary> earningSummaries = earningSummaryRepository.findAllByReviewer_Id(reviewerId);

        return earningSummaryMapper.toResponseList(earningSummaries);

    }

    @Override
    public EarningSummaryResponse getSummaryByEarningSummaryId(String earningSummaryId) {
        EarningSummary earningSummary = earningSummaryRepository.findById(earningSummaryId)
                .orElseThrow(()-> new RuntimeException("EarningSummary not found"));

        return earningSummaryMapper.toResponse(earningSummary);
    }

    @Override
    public List<EarningSummaryResponse> getSummaryByMonth(Long month) {
        if(month < 1 || month > 12) {
            throw new InvalidParameterException("Invalid month");
        }
        List<EarningSummary> earningSummaries = earningSummaryRepository.findAllByMonth(month);
        return earningSummaryMapper.toResponseList(earningSummaries);
    }

    @Override
    public List<EarningSummaryResponse> getSummaryByYear(Long year) {
        List<EarningSummary> earningSummaries = earningSummaryRepository.findAllByYear(year);
        return earningSummaryMapper.toResponseList(earningSummaries);
    }
    @Override
    @Transactional
    public void generateMonthlySummary(String reviewerId, Integer year, Integer month) {


        if (earningSummaryRepository.existsByReviewer_IdAndYearAndMonth(
                reviewerId, year, month)) {

            return;
        }

        try {

            YearMonth ym = YearMonth.of(year, month);
            LocalDateTime start = ym.atDay(1).atStartOfDay();
            LocalDateTime end = start.plusMonths(1);

            Reviewer reviewer = reviewerRepository.findById(reviewerId)
                    .orElseThrow(() -> new ReviewerNotFound("Reviewer not found"));

            User user = reviewer.getUser();

            List<EarningEvent> events =
                    earningEventRepository.findMonthlyEvents(reviewerId, start, end);

            Long likes = events.stream().filter(e -> e.getSourceType() == SourceType.LIKE).count();
            Long comments = events.stream().filter(e -> e.getSourceType() == SourceType.COMMENT).count();
            Long shares = events.stream().filter(e -> e.getSourceType() == SourceType.SHARE).count();



            long followers = user.getFollowerCount().longValue();


            BigDecimal totalAmount = events.stream()
                    .map(EarningEvent::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            EarningSummaryCreateDTO dto = new EarningSummaryCreateDTO();
            dto.setReviewerId(reviewerId);
            dto.setYear(year);
            dto.setMonth(month);
            dto.setTotalLikesCount(likes);
            dto.setTotalCommentsCount(comments);
            dto.setTotalSharesCount(shares);
            dto.setTotalFollowerCount(followers);
            dto.setTotalEarningAmount(totalAmount);

            EarningSummaryResponse saved = createSummary(dto);



            updateStatusSummary(saved.getId(), EarningSummaryStatus.CLOSED.name());

            Wallet wallet = walletRepository.findWalletByUser_Id(user.getId());
            if (wallet == null) {
                throw new RuntimeException("Wallet not found");
            }
            if(totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new  RuntimeException("Total amount must be greater than 0");
            }

            WalletTransactionCreateDTO tx = new WalletTransactionCreateDTO();
            tx.setWalletId(wallet.getId());
            tx.setAmount(totalAmount);
            tx.setTransactionType(TransactionType.DEPOSIT);
            walletTransactionService.create(tx);


        } catch (DataIntegrityViolationException e) {
            log.warn("[SUMMARY] Duplicate detected at DB level reviewer={} {}/{}",
                    reviewerId, month, year);
        }
    }



}
