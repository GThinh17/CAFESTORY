package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateDTO {

    @NotBlank(message = "Blog is required")
    private String blogId;

    private String commentParentId;

    @NotBlank(message = "Content must not be empty")
    @Size(max = 500, message = "Content must be at most 500 characters")
    private String content;

    private String commentImageId;
}
