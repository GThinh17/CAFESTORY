package vn.gt.__back_end_javaspring.DTO;

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
public class LoginDTO {
	@NotBlank(message = "email is not blank")
	private String email;

	@NotBlank(message = "password is not blank")
	private String password;



}
