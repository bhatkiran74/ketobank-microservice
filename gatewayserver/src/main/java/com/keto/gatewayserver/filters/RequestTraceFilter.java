package com.keto.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {


    public static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility utility;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();

        if (isCorrelationId(headers)){
            logger.debug("keto-correlation-id found in RequestTraceFilter :{}",utility.getCorrelationId(headers));
        }else {

            String correlationId = getCorrelationId();
            exchange = utility.setCorrelationId(exchange,correlationId);
            logger.debug("keto-correlation-id generated in RequestTraceFilter :{}",correlationId);

        }

        return chain.filter(exchange);
    }



    private boolean isCorrelationId(HttpHeaders httpHeaders){
        if (utility.getCorrelationId(httpHeaders)!=null){
            return true;
        }else
            return false;
    }

    private String getCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }
}
