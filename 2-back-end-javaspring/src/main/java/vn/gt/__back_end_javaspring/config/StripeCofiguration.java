//package vn.gt.__back_end_javaspring.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import com.stripe.Stripe;
//
//import jakarta.annotation.PostConstruct;
//
//@Configuration
//public class StripeCofiguration {
//    @Value("${stripe.secret.key}")
//    private String secretKey;
//
//    @PostConstruct
//    public void init() {
//        System.out.println("Stripe key: [" + secretKey + "]");
//
//        try {
//            Stripe.apiKey = secretKey;
//            System.out.println("Stripe initialized OK");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//}
