package vn.gt.__back_end_javaspring.DTO;

import lombok.*;
import vn.gt.__back_end_javaspring.enums.PlatformType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenRequestDTO {
    private String fcmToken;
    private PlatformType platform;
}
