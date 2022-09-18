package cane.brothers.security.oauth2.user;

import cane.brothers.user.AuthProvider;
import java.util.Map;

/**
 * @author mniedre
 */
public interface OAuth2UserInfoProvider {

  AuthProvider getAuthProvider();

  UserInfo getUserInfo(Map<String, Object> attributes);
}
