package vn.gt.__back_end_javaspring.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Payout;
import com.stripe.net.RequestOptions;

import jakarta.annotation.PostConstruct;

@Service
public class PayOutService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Account createConnectedAccount(String email) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("type", "custom");
        params.put("country", "US");
        params.put("email", email);

        return Account.create(params);
    }

    public String generateAccountLink(String accountId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("account", accountId);
        params.put("refresh_url", "http://localhost:3000/stripe/refresh");
        params.put("return_url", "http://localhost:3000/stripe/success");
        params.put("type", "account_onboarding");

        AccountLink link = AccountLink.create(params);
        return link.getUrl();
    }

    public Payout createPayout(long amount, String stripeAccountId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount * 100); // convert to cents
        params.put("currency", "usd");

        RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(stripeAccountId).build();

        return Payout.create(params, requestOptions);
    }
}
