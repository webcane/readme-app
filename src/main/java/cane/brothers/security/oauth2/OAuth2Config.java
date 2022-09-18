package cane.brothers.security.oauth2;

import cane.brothers.security.oauth2.user.GithubUserInfoProvider;
import cane.brothers.security.oauth2.user.OAuth2UserInfoProvider;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2Config {

  @Bean
  public Set<OAuth2UserInfoProvider> oAuth2UserInfo() {
    return Set.of(new GithubUserInfoProvider());
  }
}
