package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentLikeCreateDTO {
    @NotBlank(message = "User is required")
    private String userId;

    @NotBlank(message = "Comment is required")
    private String commentId;
}
