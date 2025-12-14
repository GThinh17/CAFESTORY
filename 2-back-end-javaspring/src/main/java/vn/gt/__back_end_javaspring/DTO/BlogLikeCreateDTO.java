package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogLikeCreateDTO {
    @NotBlank(message = "User is required")
    private String userId;

    @NotBlank(message = "Blog is required")
    private String blogId;
}
