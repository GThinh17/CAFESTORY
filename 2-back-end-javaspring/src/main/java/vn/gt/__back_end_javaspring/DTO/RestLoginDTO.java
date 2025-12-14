package vn.gt.__back_end_javaspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestLoginDTO {
	private String accessToken;

	private String userId;
	private String imagePath;
	private String fullname;

	public String getAccessToken() {

		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public RestLoginDTO(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

}
