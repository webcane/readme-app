package cane.brothers.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * @author mniedre
 */
@Slf4j
@Repository
@Profile("management")
public class LoggingInMemoryHttpTraceRepository extends InMemoryHttpTraceRepository {

  public void add(HttpTrace trace) {
    super.add(trace);
    log.trace("Trace:" + ToStringBuilder.reflectionToString(trace));
    log.trace("Request:" + ToStringBuilder.reflectionToString(trace.getRequest()));
    log.trace("Response:" + ToStringBuilder.reflectionToString(trace.getResponse()));
  }
}
