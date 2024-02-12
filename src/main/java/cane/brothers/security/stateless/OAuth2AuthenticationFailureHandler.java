package cane.brothers.security.stateless;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    // private final OAuth2AuthorizationCookieService cookieSvc;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
//    String targetUrl = cookieSvc.getRedirectUriCookie(request)
//            .map(Cookie::getValue)
//            .orElse(("/login"));

        String targetUrl = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        setDefaultFailureUrl(targetUrl);

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

//    cookieSvc.removeAuthorizationRequest(request, response);
//    cookieSvc.removeRedirectUri(request, response);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

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
