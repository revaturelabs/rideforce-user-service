package com.revature.securityTests;

import java.util.regex.Pattern;
import org.junit.Test;
import org.junit.Before;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifier.BaseVerification;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.revature.rideforce.user.security.LoginTokenProvider;
import com.revature.rideforce.user.UserApplication;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class LoginTokenProviderTest {
	
	private static final int USER_ID = 1;
	private Algorithm algorithm;
	private static final String ISSUER = "revature";
	private JWTVerifier verifier;
	private BaseVerification verification;
	private String testToken;
	
	@Autowired
	private LoginTokenProvider loginTokenProvider;
	
	@Before
	public void setupJwt() {
		algorithm = Algorithm.HMAC256("SECRET");
		verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
		verification = (BaseVerification) JWT.require(algorithm).withIssuer(ISSUER).acceptLeeway(1);
		testToken = loginTokenProvider.generateToken(USER_ID);
	}

	@Test
	public void testInstanceVariablesNotNull() {
		assertThat(loginTokenProvider).isNotNull();
		assertThat(algorithm).isNotNull();
		assertThat(verifier).isNotNull();
		assertThat(testToken).isNotNull();
	}

	@Test
	public void testTokenValidFormat() {
		Pattern regex = Pattern.compile("^[a-zA-Z0-9-_]+?.[a-zA-Z0-9-_]+?.([a-zA-Z0-9-_]+)?$");
		assertThat(testToken).matches(regex);
	}

	@Test
	  public void verifyToken() {
	    DecodedJWT decodedJwt = verification.build().verify(testToken);
	    String header = decodedJwt.getHeader();
	    String payload = decodedJwt.getPayload();
	    String signature = decodedJwt.getSignature();
	    assertThat(testToken).matches(header + "." +  payload + "." + signature); 
	    assertThat(decodedJwt.getSubject()).isNotNull().isInstanceOf(String.class).matches(String.valueOf(USER_ID));
	}
}
