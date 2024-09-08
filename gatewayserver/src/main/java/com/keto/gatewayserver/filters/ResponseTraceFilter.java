package com.keto.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseTraceFilter {

    public static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Autowired
    FilterUtility utility;


    @Bean
    public GlobalFilter postGlobalFilter(){
        return ((exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                HttpHeaders headers = exchange.getRequest().getHeaders();
                String correlationId = utility.getCorrelationId(headers);

                if (!exchange.getResponse().getHeaders().containsKey(utility.CORRELATION_ID)) {
                    logger.debug("update correlationId to the outbounds headers :{}", correlationId);
                    exchange.getResponse().getHeaders().add(utility.CORRELATION_ID, correlationId);
                }
            }));
        });
    }



}
