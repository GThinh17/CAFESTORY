package vn.gt.__back_end_javaspring.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    void init(){
        try{
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://cafestorychat-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Ket noi thanh cong nha");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Ket noi khong thanh cong ");
        }

    }


}
