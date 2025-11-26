//package vn.gt.__back_end_javaspring.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.rpc.context.AttributeContext.Response;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import com.stripe.param.PaymentIntentCreateParams;
//
//import lombok.RequiredArgsConstructor;
//import vn.gt.__back_end_javaspring.entity.ExtraFee;
//import vn.gt.__back_end_javaspring.entity.Payment;
//import vn.gt.__back_end_javaspring.entity.PaymentDetailed;
//import vn.gt.__back_end_javaspring.service.ExtraFeeService;
//import vn.gt.__back_end_javaspring.service.PaymentDetailedService;
//import vn.gt.__back_end_javaspring.service.PaymentService;
//import vn.gt.__back_end_javaspring.service.UserService;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class PaymentIntentController {
//
//    private final PaymentDetailedService paymentDetailedService;
//    private final PaymentService paymentService;
//    private final UserService userService;
//    private final ExtraFeeService extraFeeService;
//
//    @PostMapping("/create-payment-intent")
//    public ResponseEntity<?> createPaymentIntent(@RequestBody ExtraFee extraFee,
//            @AuthenticationPrincipal Jwt jwt)
//            throws StripeException {
//        ExtraFee production = this.extraFeeService.GetExtraFee(extraFee.getExtraFeeId());
//        if (production == null) {
//            return null;
//        }
//        Long total = production.getTotal();
//        String productionName = production.getExtraFeeName();
//        String description = production.getDescription();
//        String email = jwt.getClaimAsString("email");
//
//        Payment payment = new Payment();
//        payment.setAmount(total);
//        payment.setStatus(false);
//        payment.setName(productionName);
//        payment = this.paymentService.CreatePayment(payment);
//
//        PaymentDetailed paymentDetailed = new PaymentDetailed();
//        paymentDetailed.setExtraFee(production);
//        paymentDetailed.setPayment(payment);
//        paymentDetailed.setUser(userService.handleGetUserByEmail(email));
//        // truyen payment_detailed va thoi gian het han cua goi
//        this.paymentDetailedService.CreatePaymentDetailed(paymentDetailed, production.getExpiredTime());
//
//        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
//                .setAmount(total * 100) // Stripe tính theo "xu"
//                .setCurrency("vnd")
//                .setDescription(description)
//                .putMetadata("paymentId", payment.getPaymentId())
//                .putMetadata("extraFeeId", production.getExtraFeeId())
//                .putMetadata("email", email)
//                .build();
//
//        PaymentIntent paymentIntent = PaymentIntent.create(params);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("clientSecret", paymentIntent.getClientSecret());
//        response.put("Payment", payment);
//
//        return ResponseEntity.ok().body(response);
//    }
//}
