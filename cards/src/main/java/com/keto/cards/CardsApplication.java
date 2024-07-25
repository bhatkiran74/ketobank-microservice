package com.keto.cards;

import com.keto.cards.utils.dto.CardsContactDetailDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Cards Microservice REST API Documentation",
				version = "1.0.0",
				description = "Documentation for the Cards Microservice REST API, providing endpoints for managing card operations.",
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
				contact = @Contact(
						name = "Kiransing Bhat", email = "bhatkiran74@gmail.com")
		),
		tags = {
				@Tag(name = "Cards", description = "Operations related to card management")
		},
		servers = {
				@Server(
						description = "PROD basePath",
						url = "https://{test}.api.com/base/path/{version}/",
						variables = {
								@ServerVariable(name = "client", defaultValue = "prod"),
								@ServerVariable(name = "version", defaultValue = "v1")
						}
				),
				@Server(
						description = "UAT basePath",
						url = "https://{test}.api.com/base/path/{version}/",
						variables = {
								@ServerVariable(name = "client", defaultValue = "uat"),
								@ServerVariable(name = "version", defaultValue = "v1")
						}
				)
		},
		security = {
				@SecurityRequirement(name = "basicAuth"),
				@SecurityRequirement(name = "APIKey")
		}
)
@EnableConfigurationProperties(CardsContactDetailDto.class)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
