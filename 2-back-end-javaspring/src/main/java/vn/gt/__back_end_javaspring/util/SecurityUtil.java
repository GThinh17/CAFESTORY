package vn.gt.__back_end_javaspring.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import vn.gt.__back_end_javaspring.entity.User;
import vn.gt.__back_end_javaspring.service.UserService;

@Service
public class SecurityUtil {
	public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
	public final JwtEncoder jwtEncoder;
	public UserService userService;

	public SecurityUtil(JwtEncoder jwtEncoder, UserService userService) {
		this.jwtEncoder = jwtEncoder;
		this.userService = userService;
	}

	@Value("${jwt.base64-secret}")
	private String jwtKey;

	@Value("${jwt.token-validity-in-seconds}")
	private long jwtExpiration;

	public String createToken(Authentication authentication) {
		Instant now = Instant.now();
		Instant validity = now.plus(this.jwtExpiration, ChronoUnit.SECONDS);
		User user = this.userService.handleGetUserByEmail(authentication.getName());
		// @formatter:off
	        JwtClaimsSet claims = JwtClaimsSet.builder()
	            .issuedAt(now)
	            .expiresAt(validity)
	            .subject(authentication.getName())
	            .claim("email", authentication.getName())
				.claim("roles", authentication.getAuthorities())
				.claim("id", user.getId())
	            .build();
	        
	        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
	        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

	}
}
