package cane.brothers.security.stateless;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

public interface OAuth2AuthorizationCookieService {

  String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
  String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";


  default OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
    return getRedirectUriCookie(request)
        .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
        .orElse(null);
  }

  default Optional<Cookie> getRedirectUriCookie(HttpServletRequest request) {
    return CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME);
  }

  void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                HttpServletResponse response);

  void saveRedirectUri(HttpServletRequest request, HttpServletResponse response);

  default void removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
    CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
  }

  default void removeRedirectUri(HttpServletRequest request, HttpServletResponse response) {
    CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
  }
}
