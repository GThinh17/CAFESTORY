package vn.gt.__back_end_javaspring.DTO;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageUpdateDTO {

    private String pageName;

    private String description;
    private String avatarUrl;
    private String coverUrl;
    private String contactPhone;
    private String contactEmail;

    private JsonNode openHours;
}
