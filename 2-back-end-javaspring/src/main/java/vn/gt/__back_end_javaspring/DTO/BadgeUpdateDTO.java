package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeUpdateDTO {
    private String badgeName;

    private String description;

    private String iconUrl;

    private Boolean isDeleted;

    private Long criteriaValue;
}
