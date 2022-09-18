package cane.brothers.security.oauth2.user;

import cane.brothers.user.AuthProvider;
import java.util.Map;

public class GithubUserInfoProvider implements OAuth2UserInfoProvider {

  @Override
  public AuthProvider getAuthProvider() {
    return AuthProvider.GITHUB;
  }

  @Override
  public UserInfo getUserInfo(Map<String, Object> attributes) {
    return new GithubOAuth2UserInfo(attributes);
  }

}




