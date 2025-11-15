// ShareRequestDTO - nếu bạn chưa có, tối thiểu cần như sau:
package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShareRequestDTO {
    private String userId;

    private String blogId;

    private String visibility;
}
