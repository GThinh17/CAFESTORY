package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
<<<<<<< HEAD
import lombok.Builder;
import lombok.Data;

@Data
=======
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
>>>>>>> origin/develop
@Builder
public class CommentLikeCreateDTO {
    @NotBlank(message = "User is required")
    private String userId;

    @NotBlank(message = "Comment is required")
    private String commentId;
}
