package vn.gt.__back_end_javaspring.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.CriteriaType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BadgeCreateDTO {
    @NotBlank(message = "name is required")
    private String badgeName;

    private String description;

    private String iconUrl;

    private CriteriaType criteriaType;

    @NotNull(message = "criteria Value is required")
    private Long criteriaValue;
}
