package com.sponus.sponusbe.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	// url : http://localhost:8080/swagger-ui/index.html#/
	@Bean
	public OpenAPI getOpenApi() {
		Server server = new Server().url("/");

		return new OpenAPI()
			.info(getSwaggerInfo())
			.components(authSetting())
			.addServersItem(server);
	}

	private Info getSwaggerInfo() {
		License license = new License();
		license.setName("{Application}");

		return new Info()
			.title("Spon-Us API Document")
			.description("Spon-Us의 API 문서 입니다.")
			.version("v0.0.1")
			.license(license);
	}

	private Components authSetting() {
		return new Components()
			.addSecuritySchemes(
				"access-token",
				new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.in(SecurityScheme.In.HEADER)
					.name("Authorization"))
			.addSecuritySchemes(
				"refresh-token",
				new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.in(SecurityScheme.In.HEADER)
					.name("refreshToken"));
	}
}


