package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewerUpdateDTO {
    private String bio;

    private Integer totalScore;
}
