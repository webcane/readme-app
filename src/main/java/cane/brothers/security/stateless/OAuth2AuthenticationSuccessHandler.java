package cane.brothers.security.stateless;

import cane.brothers.AppProperties;
import cane.brothers.exception.BadRequestException;
import cane.brothers.security.jwt.JwtTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final AppProperties appProperties;
  private final JwtTokenService tokenSvc;

//  private final OAuth2AuthorizationCookieService cookieSvc;
  private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


//  public OAuth2AuthenticationSuccessHandler(AppProperties appProperties, JwtTokenService tokenSvc,
//                                            OAuth2AuthorizationCookieService cookieSvc) {
//    this.appProperties = appProperties;
//    this.tokenSvc = tokenSvc;
//    this.cookieSvc = cookieSvc;
//    setDefaultTargetUrl(OAuth2SecurityConfig.DEFAULT_LOGIN_REDIRECT_URI);
//  }

  /**
   * Calls the parent class {@code handle()} method to forward or redirect to the target
   * URL, and then calls {@code clearAuthenticationAttributes()} to remove any leftover
   * session data.
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
    //handle(request, response, authentication);
   // this.clearAuthenticationAttributes(request, response);
    String targetUrl = determineTargetUrl(request, response, authentication);

    if (response.isCommitted()) {
      logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
    }

    clearAuthenticationAttributes(request, response);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  /**
   * Removes temporary authentication-related data which may have been stored in the
   * session during the authentication process.
   */
  protected final void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
//    cookieSvc.removeAuthorizationRequest(request, response);
//    cookieSvc.removeRedirectUri(request, response);
    httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
  }

  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) {
//    Optional<String> redirectUri = cookieSvc.getRedirectUriCookie(request)
//        .map(Cookie::getValue);
    Optional<String> redirectUri = CookieUtils.getCookie(request,
        HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue);

    if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
      throw new BadRequestException(
          "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
    }

    // redirect to front-end or to default redirect url
    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
    //String targetUrl = getDefaultTargetUrl();

    String accessToken = "";

    //  Map<String, Object> claims = Map.of("iss", "readme-app",
    //        "role", "user",
    //        "sub", appProperties.auth().tokenSubject(),
    //        "aud", appProperties.auth().tokenAudience());

    //   OAuth2UserInfo oauth2UserInfo = OAuth2UserInfoFactory.getUserInfo(
    //   userRequest.getClientRegistration().getRegistrationId(),
    //   oauth2User.getAttributes());

    if (authentication instanceof OAuth2AuthenticationToken appToken) {
      var attributes = appToken.getPrincipal().getAttributes();

      var issuer = appToken.getPrincipal().getName();
      var groups = attributes.get("groups");
      var role = getMaxRole(groups);

      // generate new jwt token
      var accessClaims = new HashMap<String, Object>();

      accessClaims.put("role", role);
      accessClaims.put("email", attributes.get("email"));
      accessClaims.put("name", accessClaims.get("name"));
      accessClaims.put("username", accessClaims.get("preffered_username"));
      accessClaims.put("nickname", accessClaims.get("nickname"));
      accessClaims.put("groups", groups);

      accessToken = tokenSvc.createAccessToken(accessClaims, issuer);
    }

    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("token", accessToken)
        .build().toUriString();
  }


  private boolean isAuthorizedRedirectUri(String uri) {
    URI clientRedirectUri = URI.create(uri);
    var authorizedRedirectUri = appProperties.oauth2().authorizedRedirectUri();
    // Only validate host and port. Let the clients use different paths if they want to
    URI authorizedURI = URI.create(authorizedRedirectUri);
    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
        && authorizedURI.getPort() == clientRedirectUri.getPort();
  }

  private String getMaxRole(Object groups) {
    // todo
    return "USER";
  }
}
