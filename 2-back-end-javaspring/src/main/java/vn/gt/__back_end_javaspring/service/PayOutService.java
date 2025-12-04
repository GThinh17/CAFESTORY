package vn.gt.__back_end_javaspring.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Payout;
import com.stripe.net.RequestOptions;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.entity.User;

@Service
@RequiredArgsConstructor
public class PayOutService {
    private final UserService userService;

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Map<String, Object> createConnectedAccount(String email) throws StripeException {
        // String email = authentication.getName();
        Map<String, Object> params = new HashMap<>();
        params.put("type", "custom");
        params.put("country", "US");
        params.put("email", email);

        Map<String, Object> capabilities = new HashMap<>();
        Map<String, Object> cardPayments = new HashMap<>();
        Map<String, Object> transfers = new HashMap<>();

        cardPayments.put("requested", true);
        transfers.put("requested", true);

        capabilities.put("card_payments", cardPayments);
        capabilities.put("transfers", transfers);

        params.put("capabilities", capabilities);
        Account account = Account.create(params);

        String accountId = account.getId();
        User user = userService.handleGetUserByEmail(email);
        user.setVertifiedBank(accountId);

        user = userService.createUser(user);
        // 🔥 return JSON sạch để FE xài
        Map<String, Object> response = new HashMap<>();
        response.put("accountId", accountId);
        response.put("email", account.getEmail());
        response.put("country", account.getCountry());
        response.put("charges_enabled", account.getChargesEnabled());
        response.put("payouts_enabled", account.getPayoutsEnabled());

        return response;

    }

    public String generateAccountLink(String accountId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("account", accountId);
        params.put("refresh_url", "http://localhost:8080/stripe/refresh");
        params.put("return_url", "http://localhost:8080/stripe/success");
        params.put("type", "account_onboarding");

        AccountLink link = AccountLink.create(params);
        return link.getUrl();
    }

    public Payout createPayout(long amount, String email) throws StripeException {
        User user = userService.handleGetUserByEmail(email);

        String stripeAccountId = user.getVertifiedBank();

        if (stripeAccountId == null) {
            throw new RuntimeException("User chưa kết nối tài khoản stripe!");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount * 100); // convert to cents
        params.put("currency", "usd");

        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(stripeAccountId).build();

        return Payout.create(params, requestOptions);
    }
}
