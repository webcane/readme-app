package cane.brothers.security.login;

import cane.brothers.AppProperties;
import cane.brothers.security.jwt.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final AppProperties appProperties;
  private final JwtTokenService tokenSvc;

  public LoginAuthenticationSuccessHandler(AppProperties appProperties, JwtTokenService tokenSvc) {
    this.appProperties = appProperties;
    this.tokenSvc = tokenSvc;
    // set fallback url
    setDefaultTargetUrl("/login/token");
  }

  @Override
  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) {
    // redirect to front-end
    var targetUrl = appProperties.oauth2().authorizedRedirectUri();
    var accessToken = tokenSvc.createAccessToken();

    return UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("token", accessToken)
        .build().toUriString();
  }
}
