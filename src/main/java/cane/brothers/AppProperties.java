package cane.brothers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

/**
 * @author mniedre
 */
@ConfigurationProperties(prefix = "app")
public record AppProperties(Auth auth, OAuth2 oauth2) {

  public record Auth(String tokenAudience,
                     @DurationUnit(ChronoUnit.DAYS)
                     Duration tokenExpiration,
                     String tokenSecret,
                     String tokenSubject) {
  }

  public record OAuth2(List<String> authorizedRedirectUris) {
  }
}
