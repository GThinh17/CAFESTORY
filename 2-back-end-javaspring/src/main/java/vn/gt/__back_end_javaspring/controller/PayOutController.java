package vn.gt.__back_end_javaspring.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Review;
import com.stripe.model.Topup;
import com.stripe.model.Transfer;
import com.stripe.param.TopupCreateParams;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.gt.__back_end_javaspring.DTO.PayOutDTO;
import vn.gt.__back_end_javaspring.DTO.PayOutResponse;
import vn.gt.__back_end_javaspring.DTO.WalletResponse;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionCreateDTO;
import vn.gt.__back_end_javaspring.DTO.WalletTransactionResponse;
import vn.gt.__back_end_javaspring.entity.Payout;
import vn.gt.__back_end_javaspring.entity.Reviewer;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.entity.WalletTransaction;
import vn.gt.__back_end_javaspring.enums.TransactionType;
import vn.gt.__back_end_javaspring.repository.ReviewerRepository;
import vn.gt.__back_end_javaspring.service.PayOutService;
import vn.gt.__back_end_javaspring.service.PaymentService;
import vn.gt.__back_end_javaspring.service.ReviewerService;
import vn.gt.__back_end_javaspring.service.UserService;
import vn.gt.__back_end_javaspring.service.WalletService;
import vn.gt.__back_end_javaspring.service.WalletTransactionService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payout")
public class PayOutController {

    private final PayOutService payOutService;
    private final UserService userService;
    private final WalletService walletService;
    private final WalletTransactionService walletTransactionService;
    private final ReviewerRepository reviewerRepository;

    /*
     * ==============================
     * 1. Tạo account
     * ==============================
     */

    @PostMapping("/connect/create-account")
    public ResponseEntity<?> createAccount(@RequestParam("email") String email) throws StripeException {
        return ResponseEntity.ok(payOutService.createConnectedAccount(email));
    }

    /*
     * ==============================
     * 2. Onboarding
     * ==============================
     */
    @GetMapping("/connect/onboarding-link")
    public ResponseEntity<?> onboarding(@RequestParam String email) throws StripeException {

        User user = userService.handleGetUserByEmail(email);
        String accountId = user.getVertifiedBank();

        if (accountId == null) {
            return ResponseEntity.badRequest().body("Chưa có Stripe account");
        }

        return ResponseEntity.ok(payOutService.generateAccountLink(accountId));
    }

    /*
     * ==============================
     * 3. TRẢ TIỀN REVIEWER
     * ==============================
     */
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody vn.gt.__back_end_javaspring.DTO.TransferDTO transferDTO)
            throws StripeException {

        User reviewer = userService.handleGetUserByEmail(transferDTO.getEmail());
        // TẠO GIAO DỊCH WALLET PENDING
        WalletTransactionCreateDTO walletTransactionCreateDTO = new WalletTransactionCreateDTO();
        walletTransactionCreateDTO.setTransactionType(TransactionType.WITHDRAW);
        walletTransactionCreateDTO.setAmount(BigDecimal.valueOf(transferDTO.getAmount()));
        WalletResponse walletId = this.walletService.findByUserId(reviewer.getId());
        walletTransactionCreateDTO.setWalletId(walletId.getWalletId()); /////////// chay dc toi day
        WalletTransactionResponse walletTransactionResponse = walletTransactionService
                .create(walletTransactionCreateDTO);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + transferDTO);

        // // TẠO GIAO DỊCH RÚI TIỀN PENDING
        PayOutDTO payOutDTO = new PayOutDTO();
        Reviewer reviewer2 = this.reviewerRepository.findByUser_Id(reviewer.getId());
        payOutDTO.setReviewerId(reviewer2.getId());
        payOutDTO.setWalletId(walletId.getWalletId());
        payOutDTO.setWalletTransactionId(walletTransactionResponse.getWalletTransactionId());
        payOutDTO.setAmount(BigDecimal.valueOf(transferDTO.getAmount()));
        PayOutResponse payOutResponse = this.payOutService.createPayOut(payOutDTO);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + transferDTO);

        String accountId = reviewer.getVertifiedBank();
        Account account = Account.retrieve(accountId);

        if (accountId == null) {
            return ResponseEntity.badRequest().body("Reviewer chưa xác minh Stripe");
        }

        if (!account.getPayoutsEnabled()) {
            throw new RuntimeException("Reviewer chưa được phép nhận tiền");
        }
        System.out.println("Chay toi day" + transferDTO);
        Transfer transfer = payOutService.transferToReviewer(
                transferDTO.getAmount(),
                accountId,
                walletTransactionResponse.getWalletTransactionId(),
                payOutResponse.getId());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + transferDTO);

        return ResponseEntity.ok(Map.of(
                "transferId", transfer.getId(),
                "amount", transfer.getAmount(),
                "status", "SUCCESS"));
    }

    /*
     * ==============================
     * 4. Account status
     * ==============================
     */
    @GetMapping("/connect/account-status")
    public ResponseEntity<?> status(@RequestParam String email) throws StripeException {

        User user = userService.handleGetUserByEmail(email);
        Account account = payOutService.getAccountStatus(user.getVertifiedBank());

        return ResponseEntity.ok(Map.of(
                "charges_enabled", account.getChargesEnabled(),
                "payouts_enabled", account.getPayoutsEnabled(),
                "requirements", account.getRequirements()));
    }

    @PostMapping("/dev/fake-balance")
    public String fakeBalance() throws StripeException {
        TopupCreateParams params = TopupCreateParams.builder()
                .setAmount(5_000_000L) // $50,000.00 USD (MAX)
                // $1,000
                .setCurrency("usd")
                .build();

        Topup.create(params);
        return "OK";
    }

}
