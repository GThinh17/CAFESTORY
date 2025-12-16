package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    // private String password;
    private Long amount;
    private String email;
}
