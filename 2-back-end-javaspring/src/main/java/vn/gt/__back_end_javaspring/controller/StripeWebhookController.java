package vn.gt.__back_end_javaspring.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Transfer;
import com.stripe.net.Webhook;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerDTO;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerCreateDTO;
import vn.gt.__back_end_javaspring.DTO.ReviewerResponse;
import vn.gt.__back_end_javaspring.entity.*;
import vn.gt.__back_end_javaspring.enums.PaymentStatus;
import vn.gt.__back_end_javaspring.enums.ProductionType;
import vn.gt.__back_end_javaspring.enums.RoleType;
import vn.gt.__back_end_javaspring.repository.RoleRepository;
import vn.gt.__back_end_javaspring.repository.UserRepository;
import vn.gt.__back_end_javaspring.repository.UserRoleRepository;
import vn.gt.__back_end_javaspring.service.*;
import vn.gt.__back_end_javaspring.service.*;
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
    private final UserRoleService userRoleService;
    private final CafeOwnerService cafeOwnerService;

    @Value("${stripe.endPointSecret.key}")
    private String endpointSecret;
    private PayOutService payOutService;
    private final PaymentService paymentService;

    @Transactional
    public void handleTransferSuccess(Transfer transfer) {

        String transferId = transfer.getId();

    }

    @Transactional
    public void handleTransferFailed(Transfer transfer) {

    }

    private void handlePaymentSucceeded(Event event) {
        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);
        if (intent != null) {
            System.out.println(" Payment Success: " + intent.getId());
        }

        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();

        String rawJson = deserializer.getRawJson(); // RAW STRING JSON
        JsonObject stripeObject = JsonParser.parseString(rawJson).getAsJsonObject(); // Parse JSON

        JsonObject metadata = stripeObject.getAsJsonObject("metadata");

        String userId = metadata.get("userId").getAsString();
        User user = userRepository.findById(userId).get();

    }

    private void handlePaymentFailed(Event event) {
        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);
        if (intent != null) {
            System.out.println(" Payment Failed: " + intent.getId());
        }

        String paymentId = intent.getMetadata().get("paymentId");
        if (paymentId != null) {
            paymentService.UpdatePayment(paymentId, PaymentStatus.FAILED);
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
        try {

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
            String userId = metadata.get("userId").getAsString();
            System.out.println(" Checkout Session Completed thanh cong toi day: " + paymentId);
            // >>>>>>>>>>>>>>>>>>>>LOG ĐỂ KIỂM TRA <<<<<<<<<<<<<<<<<<<<<<
            System.out.println("Checkout Completed: " + sessionId);
            System.out.println("User Email: " + email);
            System.out.println("Client Ref Id: " + clientReferenceId);
            System.out.println("Payment Intent ID: " + paymentIntentId);
            System.out.println("Metadata PaymentID: " + paymentId);
            System.out.println("Metadata ProductionID: " + productionId);

            // Cập nhật lại trạng thái payment nếu thành công

            // String productId = metadata.get("productId").getAsString();
            Production production = productionService.GetProduction(productionId);

            User user = userRepository.findById(userId).get();

            // Tao Reviewer

            if (production.getProductionType() == ProductionType.REVIEWER) {
                ReviewerCreateDTO dto = new ReviewerCreateDTO();
                dto.setUserId(userId);
                dto.setDuration(production.getTimeExpired());
                ReviewerResponse reviewerResponse = reviewerService.registerReviewer(dto);

            } else {
                // Thieu cai dang ky
                CafeOwnerDTO cafeOwnerDTO = new CafeOwnerDTO();
                cafeOwnerDTO.setUserId(userId);
                cafeOwnerDTO.setDuration(production.getTimeExpired());
                cafeOwnerService.createCafeOwner(cafeOwnerDTO);
            }

            // Set payment thanh success
            Payment payment = this.paymentService.UpdatePayment(paymentId, PaymentStatus.SUCCESS);
            System.out.println(" Checkout Session Completed thanh toi day2: " + payment);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(">>>>>>>>>>>>>>LỖI NÈ<<<<<<<<<<<" + e);
        }
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
        Transfer transfer = (Transfer) event.getDataObjectDeserializer()
                .getObject().orElse(null);
        // Handle events from Stripe
        switch (event.getType()) {
            case "checkout.session.completed":
                handleCheckoutSessionCompleted(event); // Dang ky
                break;

            case "payment_intent.succeeded": // Nap tien
                handlePaymentSucceeded(event);
                break;

            case "payment_intent.payment_failed":
                handlePaymentFailed(event);
                break;

            case "checkout.session.expired":
                handlePaymentFailed(event);
                break;

            case "transfer.created":
                handleTransferSuccess(transfer);

            case "transfer.failed":
                handleTransferFailed(transfer);

            default:
                System.out.println("⚠ Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Success");
    }

}
