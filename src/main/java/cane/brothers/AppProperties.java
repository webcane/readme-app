package cane.brothers;

import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mniedre
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(Auth auth, OAuth2 oauth2) {


  public record Auth(String tokenSecret, Duration tokenExpiration, Duration cookieExpiration) {}

  public record OAuth2(List<String> authorizedRedirectUris) {}
}
