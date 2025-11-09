package vn.gt.__back_end_javaspring.entity.DTO;

public class RestLoginDTO {
	private String accessToken;

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
