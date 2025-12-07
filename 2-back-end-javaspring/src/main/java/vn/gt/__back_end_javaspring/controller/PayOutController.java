package vn.gt.__back_end_javaspring.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Payout;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.service.PayOutService;
import vn.gt.__back_end_javaspring.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payout")
public class PayOutController {

    private final UserService userService;
    @Autowired
    private PayOutService payOutService;

    @PostMapping("/create-account")
    public Map<String, Object> createAccount(@RequestParam String email) throws StripeException {
        return payOutService.createConnectedAccount(email);
    }

    @GetMapping("/onboarding-link")
    public ResponseEntity<?> onboardingLink(@RequestParam String email) throws StripeException {
        User user = this.userService.handleGetUserByEmail(email);
        String accountId = user.getVertifiedBank();
        if (accountId == null) {
            return ResponseEntity.status(500).body("Chưa xác nhận tài khoản thanh toán");
        }
        String link = payOutService.generateAccountLink(accountId);
        return ResponseEntity.ok().body(link);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam long amount, @RequestParam String email) throws StripeException {
        return ResponseEntity.ok().body(payOutService.createPayout(amount, email));
    }

    @GetMapping("/account-status")
    public ResponseEntity<?> getAccountStatus(@RequestParam String email) throws StripeException {
        User user = this.userService.handleGetUserByEmail(email);
        String accountId = user.getVertifiedBank();

        if (accountId == null) {
            return ResponseEntity.status(400).body(Map.of(
                    "success", false,
                    "message", "User chưa tạo Stripe Connected Account"));
        }

        Account account = payOutService.getAccountStatus(accountId);

        Map<String, Object> response = Map.of(
                "accountId", accountId,
                "charges_enabled", account.getChargesEnabled(),
                "payouts_enabled", account.getPayoutsEnabled(),
                "requirements", account.getRequirements().getCurrentlyDue(),
                "past_due", account.getRequirements().getPastDue());

        return ResponseEntity.ok(response);
    }
}
