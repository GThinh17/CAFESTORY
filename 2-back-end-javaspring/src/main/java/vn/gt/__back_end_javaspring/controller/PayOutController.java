package vn.gt.__back_end_javaspring.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.service.PayOutService;
import vn.gt.__back_end_javaspring.service.UserService;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payout")
public class PayOutController {

    private final PayOutService payOutService;
    private final UserService userService;

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
    public ResponseEntity<?> transfer(
            @RequestParam Long amount,
            @RequestParam String email) throws StripeException {

        User reviewer = userService.handleGetUserByEmail(email);
        String accountId = reviewer.getVertifiedBank();
        Account account = Account.retrieve(accountId);

        if (!account.getPayoutsEnabled()) {
            throw new RuntimeException("Reviewer chưa được phép nhận tiền");
        }
        if (reviewer.getVertifiedBank() == null) {
            return ResponseEntity.badRequest().body("Reviewer chưa xác minh Stripe");
        }

        Transfer transfer = payOutService.transferToReviewer(
                amount,
                reviewer.getVertifiedBank());

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
}
