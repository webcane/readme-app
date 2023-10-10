package cane.brothers.spring;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.Include;
import org.springframework.boot.actuate.web.exchanges.servlet.HttpExchangesFilter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author mniedre
 */
@Slf4j
@Component
@Profile("management")
public class FilterableHttpTraceFilter extends HttpExchangesFilter {

  private List<String> excludes = Arrays.asList("actuator", "management", "favicon", "build", "swagger", "swagger-ui");

  /**
   * Create a new {@link HttpExchangesFilter} instance.
   *
   * @param repository the repository used to record events
   * @param includes   the include options
   */
  public FilterableHttpTraceFilter(HttpExchangeRepository repository, Set<Include> includes) {
    super(repository, includes);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String reqPath = request.getRequestURI();
    boolean exclude = excludes.stream().anyMatch(ex -> reqPath.contains(ex));
    if (!exclude) {
      log.trace(reqPath);
    }
    return exclude;
  }
}
