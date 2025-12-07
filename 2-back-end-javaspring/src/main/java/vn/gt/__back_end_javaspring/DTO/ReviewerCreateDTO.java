package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewerCreateDTO {
    @NotBlank(message = "UserId is required")
    private String userId;

    private String bio;

    private Integer duration;
}
