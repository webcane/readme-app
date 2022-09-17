package cane.brothers.security.oauth2;

import org.springframework.security.core.AuthenticationException;

/**
 * @author mniedre
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {

  public OAuth2AuthenticationProcessingException(String msg) {
    super(msg);
  }
}
