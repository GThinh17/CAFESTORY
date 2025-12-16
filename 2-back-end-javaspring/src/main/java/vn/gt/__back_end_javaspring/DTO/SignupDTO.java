package vn.gt.__back_end_javaspring.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupDTO {

	@NotBlank(message = "fullname is not blank")
	private String fullname;

	@Size(min = 5, max = 15)
	@NotBlank(message = "phone is not blank")
	private String phone;

	@Size(min = 6, max = 50)
	@NotBlank(message = "password is not blank")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Email
	@NotBlank(message = "email is not blank")
	private String email;
	
	private String location;


	private String avatar;

	private String address;

	private String dateOfBirth;

}
