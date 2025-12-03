package vn.gt.__back_end_javaspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Payout;

import vn.gt.__back_end_javaspring.service.PayOutService;

@RestController
@RequestMapping("/api/payout")
public class PayOutController {

    @Autowired
    private PayOutService payOutService;

    @PostMapping("/create-account")
    public Account createAccount(@RequestParam String email) throws StripeException {
        return payOutService.createConnectedAccount(email);
    }

    @GetMapping("/onboarding-link")
    public String onboardingLink(@RequestParam String accountId) throws StripeException {
        return payOutService.generateAccountLink(accountId);
    }

    @PostMapping("/withdraw")
    public Payout withdraw(@RequestParam long amount, @RequestParam String stripeAccountId) throws StripeException {
        return payOutService.createPayout(amount, stripeAccountId);
    }
}
