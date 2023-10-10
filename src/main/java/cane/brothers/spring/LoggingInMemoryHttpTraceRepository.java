package cane.brothers.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * @author mniedre
 */
@Slf4j
@Repository
@Profile("management")
public class LoggingInMemoryHttpTraceRepository extends InMemoryHttpExchangeRepository {

    public LoggingInMemoryHttpTraceRepository() {
        setCapacity(500);
    }

    public void add(HttpExchange exchange) {
        super.add(exchange);
        log.trace("Trace:" + ToStringBuilder.reflectionToString(exchange));
        log.trace("Request:" + ToStringBuilder.reflectionToString(exchange.getRequest()));
        log.trace("Response:" + ToStringBuilder.reflectionToString(exchange.getResponse()));
    }
}
