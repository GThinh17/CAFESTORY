package vn.gt.__back_end_javaspring.DTO;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageCreateDTO {

    @NotBlank
    private String cafeOwnerId;

    @NotBlank
    private String pageName;

    private String slug;
    private String description;
    private String avatarUrl;
    private String coverUrl;
    private String contactPhone;

    @Email
    private String contactEmail;

    private JsonNode openHours;
}
