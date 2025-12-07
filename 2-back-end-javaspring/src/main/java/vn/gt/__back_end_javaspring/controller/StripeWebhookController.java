package vn.gt.__back_end_javaspring.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.PaymentStatus;
import vn.gt.__back_end_javaspring.enums.ProductionType;
import vn.gt.__back_end_javaspring.enums.RoleType;
import vn.gt.__back_end_javaspring.repository.RoleRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.repository.UserRoleRepository;
import vn.gt.__back_end_javaspring.service.PaymentService;
import vn.gt.__back_end_javaspring.service.ProductionService;
import vn.gt.__back_end_javaspring.service.ReviewerService;
import vn.gt.__back_end_javaspring.service.impl.ReviewerStatusScheduler;

@RestController
@RequestMapping("/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {
    private final ReviewerStatusScheduler reviewerStatusScheduler;
    private final ProductionService productionService;
    private final ReviewerService reviewerService;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Value("${stripe.endPointSecret.key}")
    private String endpointSecret;

    private final PaymentService paymentService;

    private void handlePaymentSucceeded(Event event) {
        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);
        if (intent != null) {
            System.out.println(" Payment Success: " + intent.getId());
        }

    }

    private void handlePaymentFailed(Event event) {
        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);
        if (intent != null) {
            System.out.println(" Payment Failed: " + intent.getId());
        }
    }

    private void handlePaymentExpired(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

        String rawJson = deserializer.getRawJson(); // RAW STRING JSON
        JsonObject stripeObject = JsonParser.parseString(rawJson).getAsJsonObject(); // Parse JSON
        String sessionId = stripeObject.get("id").getAsString();
        String email = stripeObject.get("customer_email").getAsString();
        String clientReferenceId = stripeObject.get("client_reference_id").getAsString();
        String paymentIntentId = stripeObject.get("payment_intent").getAsString();
        JsonObject metadata = stripeObject.getAsJsonObject("metadata");
        String paymentId = metadata.get("paymentId").getAsString();

        Payment payment = this.paymentService.UpdatePayment(paymentId, PaymentStatus.EXPIRED);

    }

    private void handleCheckoutSessionCompleted(Event event) {
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

        String rawJson = deserializer.getRawJson(); // RAW STRING JSON
        JsonObject stripeObject = JsonParser.parseString(rawJson).getAsJsonObject(); // Parse JSON

        // Lấy thông tin Session
        String sessionId = stripeObject.get("id").getAsString();
        String email = stripeObject.get("customer_email").getAsString();
        String clientReferenceId = stripeObject.get("client_reference_id").getAsString();
        String paymentIntentId = stripeObject.get("payment_intent").getAsString();

        // Metadata
        JsonObject metadata = stripeObject.getAsJsonObject("metadata");
        String paymentId = metadata.get("paymentId").getAsString();
        String productionId = metadata.get("productionId").getAsString();

        System.out.println(" Checkout Session Completed thanh cong toi day: " + paymentId);
        // >>>>>>>>>>>>>>>>>>>>LOG ĐỂ KIỂM TRA <<<<<<<<<<<<<<<<<<<<<<
        // System.out.println("Checkout Completed: " + sessionId);
        // System.out.println("User Email: " + email);
        // System.out.println("Client Ref Id: " + clientReferenceId);
        // System.out.println("Payment Intent ID: " + paymentIntentId);
        // System.out.println("Metadata PaymentID: " + paymentId);
        // System.out.println("Metadata ProductionID: " + productionId);

        // Cập nhật lại trạng thái payment nếu thành công

        String productId = metadata.get("productId").getAsString();
        Production production = productionService.GetProduction(productId);

        String userId = metadata.get("userId").getAsString();
        User user = userRepository.findById(userId).get();
        //Tao Reviewer
        ReviewerCreateDTO dto = new ReviewerCreateDTO();
        dto.setUserId(metadata.getAsJsonObject().get("user_id").getAsString());
        dto.setDuration(production.getTimeExpired());

        reviewerService.registerReviewer(dto);

        //Set Role
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        if(production.getProductionType() == ProductionType.REVIEWER){
            Role role = roleRepository.findByroleName(RoleType.REVIEWER);
            userRole.setRole(role);
        } else{
            Role role = roleRepository.findByroleName(RoleType.CAFEOWNER);
            userRole.setRole(role);
        }
        userRoleRepository.save(userRole);


        //Set payment thanh success
        Payment payment = this.paymentService.UpdatePayment(paymentId, PaymentStatus.SUCCESS);
        //System.out.println(" Checkout Session Completed thanh toi day2: " + paymentId);

        //System.out.println(payment);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(
            HttpServletRequest request,
            @RequestHeader("Stripe-Signature") String signature) throws IOException {

        String payload = new String(request.getInputStream().readAllBytes(), "UTF-8");
        Event event;
        System.out.println(">>>> RAW PAYLOAD <<<<");
        System.out.println(payload);
        try {
            // Verify signature from Stripe
            event = Webhook.constructEvent(payload, signature, endpointSecret);

        } catch (Exception e) {
            System.out.println(" Invalid Signature: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid Signature");
        }

        System.out.println(" Stripe Event Received: " + event.getType());
        // Handle events from Stripe
        switch (event.getType()) {
            case "checkout.session.completed":
                handleCheckoutSessionCompleted(event); //Dang ky
                break;

            case "payment_intent.succeeded": //Nap tien
                handlePaymentSucceeded(event);
                break;

            case "payment_intent.payment_failed":
                handlePaymentFailed(event);
                break;
            case "checkout.session.expired":
                handlePaymentFailed(event);
                break;

            default:
                System.out.println("⚠ Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Success");
    }

}
