package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PricingRuleResponse {
    private String pricingRuleResponseId;

    private String name;

    private String description;

    private BigDecimal likeWeight;

    private BigDecimal commentWeight;

    private BigDecimal shareWeight;

    private BigDecimal unitPrice;

    private String currency;

    private LocalDateTime effectiveFrom;

    private LocalDateTime effectiveTo;

    private Boolean isActive;

    private LocalDateTime createdAt;
}
