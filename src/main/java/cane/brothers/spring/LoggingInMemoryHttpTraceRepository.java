package cane.brothers.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mniedre
 */
@Slf4j
@Repository
public class LoggingInMemoryHttpTraceRepository extends InMemoryHttpTraceRepository {

    public void add(HttpTrace trace) {
        super.add(trace);
        log.info("Trace:" + ToStringBuilder.reflectionToString(trace));
        log.info("Request:" + ToStringBuilder.reflectionToString(trace.getRequest()));
        log.info("Response:" + ToStringBuilder.reflectionToString(trace.getResponse()));
    }
}
