package cane.brothers.security.oauth2.user;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author mniedre
 */
@RequiredArgsConstructor
@Getter
public abstract class OAuth2UserInfo implements UserInfo {

  protected final Map<String, Object> attributes;

}
