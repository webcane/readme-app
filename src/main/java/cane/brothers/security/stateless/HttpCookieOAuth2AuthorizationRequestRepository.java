package cane.brothers.security.stateless;

import cane.brothers.AppProperties;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class HttpCookieOAuth2AuthorizationRequestRepository implements
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

  public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
  public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
//  private final OAuth2AuthorizationCookieService cookieSvc;
  private final AppProperties appProperties;

  @Override
  public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
   // return cookieSvc.loadAuthorizationRequest(request);
    return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
        .orElse(null);
  }


  @Override
  public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                       HttpServletResponse response) {
    if (authorizationRequest == null) {
//      cookieSvc.removeAuthorizationRequest(request, response);
//      cookieSvc.removeRedirectUri(request, response);
      this.removeAuthorizationRequestCookies(request, response);
    } else {
//      cookieSvc.saveAuthorizationRequest(authorizationRequest, request, response);
//      cookieSvc.saveRedirectUri(request, response);
      CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
          CookieUtils.serialize(authorizationRequest), (int) appProperties.auth().cookieExpiration().toSeconds());

      String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
      if(StringUtils.isNotBlank(redirectUriAfterLogin)) {
        CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME,
            redirectUriAfterLogin, (int) appProperties.auth().cookieExpiration().toSeconds());
      }
    }
  }

  @Override
  public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                               HttpServletResponse response) {
    return this.loadAuthorizationRequest(request);
  }

  public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
    CookieUtils.deleteCookie(request,response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    CookieUtils.deleteCookie(request,response, REDIRECT_URI_PARAM_COOKIE_NAME);
  }
}
