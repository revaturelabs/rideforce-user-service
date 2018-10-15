package com.revature.securityTests;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.revature.rideforce.user.security.LoginTokenProvider;
import com.revature.rideforce.user.UserApplication;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Slf4j
@Transactional
public class LoginTokenProviderTest {
  private static final int USER_ID = 1;
	
	@Autowired
	private LoginTokenProvider loginTokenProvider;

  @Test
  public void testAutowiredCorrectly() {
    assertThat(loginTokenProvider).isNotNull();
  }

  @Test
  public void testTokenNotNull() {
    assertThat(loginTokenProvider.generateToken(USER_ID)).isNotNull();
  }
  Consumer<String> jwtPropertyRequirements = jwt -> {
    boolean isValidFormat = jwt.matches("^[a-zA-Z0-9]+?.[a-zA-Z0-9]+?.([a-zA-Z0-9]+)?$");
    assertThat(isValidFormat).isTrue();
  };
  @Test
  public void testTokenValidFormat() {
    Object token = loginTokenProvider.generateToken(USER_ID);
    assertThat(loginTokenProvider.generateToken(USER_ID)).isInstanceOfSatisfying(String.class, jwtPropertyRequirements); 
  }
}
