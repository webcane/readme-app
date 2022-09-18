package cane.brothers.user;

import org.springframework.util.Assert;

/**
 * @author mniedre
 */
public enum AuthProvider {
  LOCAL,
  GITHUB;
//    GOOGLE,
//    FACEBOOK

  public static AuthProvider get(String provider) {
    // TODO unknown throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    Assert.notNull(provider, "Authentication provider must not be null");
    return AuthProvider.valueOf(provider.toUpperCase());
  }
}
