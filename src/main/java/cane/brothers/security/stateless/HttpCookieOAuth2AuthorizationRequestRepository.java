package cane.brothers.security.stateless;

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
  private final OAuth2AuthorizationCookieService cookieSvc;

  @Override
  public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
    return cookieSvc.loadAuthorizationRequest(request);
  }


  @Override
  public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                       HttpServletResponse response) {
    if (authorizationRequest == null) {
      cookieSvc.removeAuthorizationRequest(request, response);
      cookieSvc.removeRedirectUri(request, response);
    } else {

      cookieSvc.saveAuthorizationRequest(authorizationRequest, request, response);
      cookieSvc.saveRedirectUri(request, response);
    }
  }

  @Override
  public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
                                                               HttpServletResponse response) {
    return this.loadAuthorizationRequest(request);
  }
}
