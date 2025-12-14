package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewerCreateDTO {
    @NotBlank(message = "UserId is required")
    private String userId;

    @Min(1)
    @Max(20)
    private Integer duration;
}
