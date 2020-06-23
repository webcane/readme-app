package cane.brothers.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author mniedre
 */
@Slf4j
@Component
@Profile("management")
public class FilterableHttpTraceFilter extends HttpTraceFilter {

    private List<String> excludes = Arrays.asList("actuator", "management", "favicon", "build");

    @Autowired
    public FilterableHttpTraceFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
        super(repository, tracer);
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
