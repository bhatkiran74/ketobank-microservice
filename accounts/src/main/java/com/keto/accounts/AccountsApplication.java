package com.keto.accounts;

import com.keto.accounts.utils.dto.AccountContactDetailDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				version = "1.0.0",
				description = "Explore comprehensive documentation for our Accounts Microservice REST API, providing developers with detailed endpoints and functionality to seamlessly integrate financial account management into your applications. Gain insights into authentication, data formats, error handling, and more, empowering your team to efficiently leverage our secure and scalable API for enhanced financial services.",
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
				contact =@Contact(
						name = "Kiransing Bhat",email="bhatkiran74@gmail.com")
		),
		tags = {
				@Tag(name = "CUT1", description = "CUT Environment for Developer"),
				@Tag(name = "ETE1", description = "ETE Environment for QA")
		},
		servers = { @Server(
				description = "PROD basePath",
				url = "https://{test}.api.com/base/path/{version}/",
				variables = {
						@ServerVariable(name="client", defaultValue = "prod"),
						@ServerVariable(name="version", defaultValue = "v1")}),
				@Server(
						description = "UAT basePath",
						url = "https://{test}.api.com/base/path/{version}/",
						variables = {
								@ServerVariable(name="client", defaultValue = "uat"),
								@ServerVariable(name="version", defaultValue = "v1")})        },
		security = {
				@SecurityRequirement(name = "basicAuth"),
				@SecurityRequirement(name = "APIKey")}
)

@SecuritySchemes({
		@SecurityScheme(
				name = "basicAuth",
				type = SecuritySchemeType.HTTP,
				scheme = "basic"
		),
		@SecurityScheme(
				name = "APIKey",
				type = SecuritySchemeType.APIKEY,
				in = SecuritySchemeIn.HEADER
		)
})

@EnableConfigurationProperties(AccountContactDetailDto.class)
@EnableFeignClients
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
