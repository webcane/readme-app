package cane.brothers.security.stateless;

import cane.brothers.AppProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationCookieSvc implements OAuth2AuthorizationCookieService {

  private final AppProperties appProperties;

  @Override
  public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                       HttpServletResponse response) {
    CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
        CookieUtils.serialize(authorizationRequest), (int) appProperties.auth().cookieExpiration().toSeconds());
  }

  @Override
  public void saveRedirectUri(HttpServletRequest request, HttpServletResponse response) {
    String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

    if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
      CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin,
          (int) appProperties.auth().cookieExpiration().toSeconds());
    }
  }

}
