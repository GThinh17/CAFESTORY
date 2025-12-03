package vn.gt.__back_end_javaspring.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.gt.__back_end_javaspring.entity.RestResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();
	private final ObjectMapper objectMapper;

	public CustomAuthenticationEntryPoint(ObjectMapper mapper, PasswordEncoder passwordEncoder) {
		this.objectMapper = mapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub\
		this.delegate.commence(request, response, authException);
		response.setContentType("application/json;charset=UTF-8");

		RestResponse<Object> rest = new RestResponse<Object>();
		rest.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		rest.setErrors(authException.getCause().getMessage());
		rest.setMessage("Token không hợp lệ hoặc là đã hết hạn, không đúng định dạng");

		objectMapper.writeValue(response.getWriter(), rest);
	}

}
