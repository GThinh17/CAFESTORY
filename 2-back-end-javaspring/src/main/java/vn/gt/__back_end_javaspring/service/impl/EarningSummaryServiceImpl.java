package vn.gt.__back_end_javaspring.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void generateMonthlySummary(String reviewerId, Integer year, Integer month) {
        if (earningSummaryRepository
                .existsByReviewer_IdAndYearAndMonth(reviewerId, year, month)) {
            throw new RuntimeException("EarningSummary already exists");
        }

        LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime end = start.plusMonths(1);
        System.out.println("Thoi gian bat dau : " + start);
        System.out.println("Thoi gian ket thuc : " + end);


        List<EarningEvent> earningEvents = earningEventRepository.findMonthlyEvents(reviewerId, start, end);
        System.out.println("earningEvents size: " + earningEvents.size());

        Reviewer reviewer = reviewerRepository.findById(reviewerId)
                .orElseThrow(()-> new ReviewerNotFound("Reviewer not found"));

        User user =reviewer.getUser();

        Long totalLikesCount = earningEvents.stream()
                .filter(e->e.getSourceType() == SourceType.LIKE)
                .count();

        Long totalCommentsCount = earningEvents.stream()
                .filter(e-> e.getSourceType() == SourceType.COMMENT)
                .count();
        Long totalSharesCount = earningEvents.stream()
                .filter(e->e.getSourceType()==SourceType.SHARE)
                .count();

        Long totalFollowerCount = user.getFollowerCount().longValue();

        BigDecimal totalEarningAmount = BigDecimal.ZERO;

        for(EarningEvent earningEvent : earningEvents) {
            BigDecimal amount = earningEvent.getAmount();
            totalEarningAmount = totalEarningAmount.add(amount);
        }


        EarningSummaryCreateDTO dto = new EarningSummaryCreateDTO();
        dto.setReviewerId(reviewerId);
        dto.setYear(year);
        dto.setMonth(month);
        dto.setTotalLikesCount(totalLikesCount);
        dto.setTotalCommentsCount(totalCommentsCount);
        dto.setTotalSharesCount(totalSharesCount);
        dto.setTotalFollowerCount(totalFollowerCount);
        dto.setTotalEarningAmount(totalEarningAmount);

        EarningSummaryResponse saved = createSummary(dto);
        updateStatusSummary(saved.getId(), EarningSummaryStatus.CLOSED.toString());


        //COng tien vao wallet
        Wallet wallet = walletRepository.findWalletByUser_Id(user.getId());

        WalletTransactionCreateDTO walletTransactionCreateDTO = new WalletTransactionCreateDTO();
        walletTransactionCreateDTO.setWalletId(wallet.getId());
        walletTransactionCreateDTO.setAmount(totalEarningAmount);
        walletTransactionCreateDTO.setTransactionType(TransactionType.DEPOSIT);

    }


}
