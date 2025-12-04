package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PricingRuleCreateDTO {

    private String name;

    private String description;

    @NotNull(message = "likeWeight is required")
    @DecimalMin("0")
    @DecimalMax("100")
    private BigDecimal likeWeight;

    @NotNull(message = "commentWeight is required")
    @DecimalMin("0")
    @DecimalMax("100")
    private BigDecimal commentWeight;

    @NotNull(message = "shareWeight is required")
    @DecimalMin("0")
    @DecimalMax("100")
    private BigDecimal shareWeight;

    @NotNull(message = "unitPrice is required")
    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    @NotNull(message = "effectiveFrom is required")
    private LocalDateTime effectiveFrom;

    @NotNull(message = "effectiveTo is required")
    private LocalDateTime effectiveTo;

}
