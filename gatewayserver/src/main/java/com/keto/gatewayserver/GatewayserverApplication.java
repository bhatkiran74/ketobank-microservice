package com.keto.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}


	@Bean
	public RouteLocator ketoBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/ketobank/accounts/**")
						.filters( f -> f.rewritePath("/ketobank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/ketobank/cards/**")
						.filters( f -> f.rewritePath("/ketobank/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://CARDS"))
				.route(p -> p
						.path("/ketobank/loans/**")
						.filters( f -> f.rewritePath("/ketobank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
						)
						.uri("lb://LOANS"))
				.build();
	}

}
