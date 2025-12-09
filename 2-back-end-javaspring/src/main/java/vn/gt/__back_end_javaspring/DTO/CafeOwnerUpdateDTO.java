package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CafeOwnerUpdateDTO {
    private String businessName;

    private Integer totalReview;

    private BigDecimal averageRating;

    private String contactEmail;

    private String contactPhone;
}
