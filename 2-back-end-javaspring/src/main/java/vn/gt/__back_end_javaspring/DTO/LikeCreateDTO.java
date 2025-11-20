package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class LikeCreateDTO {
    @NotBlank(message = "User is required")
    private String userId;

    @NotBlank(message = "Blog is required")
    private String blogId;
}
