package cane.brothers.security.oauth2.user;

import cane.brothers.security.oauth2.OAuth2AuthenticationProcessingException;
import cane.brothers.user.AuthProvider;
import java.util.Map;

/**
 * @author mniedre
 */
public class OAuth2UserInfoFactory {

  /**
   * get OAuth2 UserInfo
   *
   * @param registrationId registrationId
   * @param attributes OAuth2 user info attributes
   * @return OAuth2UserInfo
   */
  public static OAuth2UserInfo getUserInfo(String registrationId, Map<String, Object> attributes) {
    if (registrationId.equalsIgnoreCase(AuthProvider.GITHUB.toString())) {
      return new GithubOAuth2UserInfo(attributes);
    } else {
      throw new OAuth2AuthenticationProcessingException(
          "Sorry! Login with " + registrationId + " is not supported yet.");
    }
  }
}
