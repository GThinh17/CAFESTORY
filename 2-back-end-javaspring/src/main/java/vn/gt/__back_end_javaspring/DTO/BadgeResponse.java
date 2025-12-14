package vn.gt.__back_end_javaspring.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.gt.__back_end_javaspring.enums.CriteriaType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BadgeResponse {
    private String badgeId;
    private String badgeName;

    private String description;

    private String iconUrl;

    private Boolean isDeleted;


    private CriteriaType criteriaType;

    private Long criteriaValue;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
