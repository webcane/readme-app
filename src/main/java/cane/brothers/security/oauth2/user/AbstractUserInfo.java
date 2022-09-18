package cane.brothers.security.oauth2.user;

import java.util.Map;

public abstract class AbstractUserInfo implements UserInfo {

  protected Map<String, Object> attributes;

  protected AbstractUserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }
}
