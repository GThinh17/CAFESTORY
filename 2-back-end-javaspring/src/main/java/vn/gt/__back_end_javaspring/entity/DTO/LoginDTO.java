package vn.gt.__back_end_javaspring.entity.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
	@NotBlank(message = "useranme is not blank")
	private String username;

	@NotBlank(message = "password is not blank")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public LoginDTO() {
		super();
	}

}
