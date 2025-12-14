package vn.gt.__back_end_javaspring.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.gt.__back_end_javaspring.DTO.NotificationRequestDTO;

@Service

public class NotificationClient {
    private final RestTemplate restTemplate; //Dung de goi backend khac
    public NotificationClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendNotification(NotificationRequestDTO requestDTO){
        restTemplate.postForObject(
                "http://localhost:8081/notification",
                requestDTO,
                String.class
        );
    }

}
