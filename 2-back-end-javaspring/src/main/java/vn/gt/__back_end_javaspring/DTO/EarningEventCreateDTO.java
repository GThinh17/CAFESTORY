package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarningEventCreateDTO {
    private String reviewerId;

    private String pricingRuleId;

    private String blogId;

    private BigDecimal amount;

    private String sourceType;

    private String commentId;

    private String likeId;

    private String shareId;

}
