package vn.gt.__back_end_javaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //bat scheduling trong SPringboot
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
