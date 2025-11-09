package vn.gt.__back_end_javaspring.entity.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogRequest {
    @NotBlank(message = "Content must not be empty")
    @Size(max = 10000, message = "Content must be at most 10000 characters")
    private String content;


    @URL(message = "imageUrl must be a valid URL")
    private String imageUrl;

    private boolean status;

    private LocalDateTime createAt = LocalDateTime.now();

    @NotNull(message = "userId is required")
    private String userId;


}
