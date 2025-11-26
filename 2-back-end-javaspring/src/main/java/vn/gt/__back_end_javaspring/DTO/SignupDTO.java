package vn.gt.__back_end_javaspring.DTO;

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
	private String password;

	@Email
	@NotBlank(message = "email is not blank")
	private String email;

    private String avatar;

    private String address;

    private String dateOfBirth;


	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
