package cane.brothers.security.oauth2.user;

import cane.brothers.user.AuthProvider;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author mniedre
 */
@Component
public class OAuth2UserInfoFactory {

  private final Map<AuthProvider, OAuth2UserInfoProvider> userInfoMap;

  @Autowired
  private OAuth2UserInfoFactory(Set<OAuth2UserInfoProvider> userInfoSet) {
    userInfoMap = userInfoSet.stream()
        .collect(Collectors.toUnmodifiableMap(OAuth2UserInfoProvider::getAuthProvider, Function.identity()));
  }

  /**
   * get OAuth2 UserInfo
   *
   * @param authProvider authProvider. for example GITHUB
   * @param attributes OAuth2 user info attributes
   * @return OAuth2UserInfo
   */
  public UserInfo getUserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        return Optional.ofNullable(userInfoMap.get(authProvider))
            .map(u -> u.getUserInfo(attributes))
        .orElseThrow(IllegalArgumentException::new);
  }
}
