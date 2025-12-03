package vn.gt.__back_end_javaspring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.rpc.context.AttributeContext.Response;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.PaymentCreateDTO;
import vn.gt.__back_end_javaspring.entity.ExtraFee;
import vn.gt.__back_end_javaspring.entity.Payment;
import vn.gt.__back_end_javaspring.entity.PaymentMethod;
import vn.gt.__back_end_javaspring.entity.Production;
import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.service.ExtraFeeService;
import vn.gt.__back_end_javaspring.service.PaymentMethodService;
import vn.gt.__back_end_javaspring.service.PaymentService;
import vn.gt.__back_end_javaspring.service.ProductionService;
import vn.gt.__back_end_javaspring.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentIntentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final ProductionService productionService;
    private final PaymentMethodService paymentMethodService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentCreateDTO paymentCreateDTO,
            @AuthenticationPrincipal Jwt jwt)
            throws StripeException {
        String productionId = paymentCreateDTO.getProductionId();
        String paymentMethodId = paymentCreateDTO.getPaymentMethodId();
        var amount = paymentCreateDTO.getAmount();
        String email = jwt.getClaimAsString("email");

        User user = this.userService.handleGetUserByEmail(email);
        Production production = this.productionService.GetProduction(productionId);
        PaymentMethod paymentMethod = this.paymentMethodService.GetPaymentMethodService(paymentMethodId);

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setUser(user);
        payment.setPaymentMethod(paymentMethod);
        payment.setProduction(production);
        payment = this.paymentService.CreatePayment(payment);

        Long Amount = Long.valueOf(amount) * Long.valueOf(production.getTotal());
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(Amount) // Stripe tính theo "xu"
                .setCurrency("usd")
                .setDescription(production.getDescription())
                .putMetadata("paymentId", payment.getPaymentId())
                .putMetadata("extraFeeId", production.getProductionId())
                .putMetadata("email", user.getEmail())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        Map<String, Object> response = new HashMap<>();
        response.put("clientSecret", paymentIntent.getClientSecret());
        response.put("Payment", payment);

        return ResponseEntity.ok().body(response);
    }
}
