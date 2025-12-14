package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class UserResponseDTO {
    private String avatar;

    private String fullName;

    private String email;

    private String id;

    private String address;

    Integer followerCount;

    private String vertifiedBank;

}
