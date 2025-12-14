package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {
    private String avatar;

    private String fullName;

    private String email;

    private String id;

    private String address;

    Integer followerCount;

    private String vertifiedBank;
}
