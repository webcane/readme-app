package cane.brothers.security.stateless;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  private final OAuth2AuthorizationCookieService cookieSvc;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
    String targetUrl = cookieSvc.getRedirectUriCookie(request)
            .map(Cookie::getValue)
            .orElse(("/login"));

    setDefaultFailureUrl(targetUrl);

    targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("error", exception.getLocalizedMessage())
        .build().toUriString();

    cookieSvc.removeAuthorizationRequest(request, response);
    cookieSvc.removeRedirectUri(request, response);

    if (isUseForward()) {
      logger.debug("Forwarding to " + targetUrl);
      request.getRequestDispatcher(targetUrl)
          .forward(request, response);
    } else {
      logger.debug("Redirecting to " + targetUrl);
      getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
  }
}
