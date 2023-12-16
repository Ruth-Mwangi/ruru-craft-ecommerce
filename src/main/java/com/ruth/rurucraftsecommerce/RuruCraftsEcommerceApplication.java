package com.ruth.rurucraftsecommerce;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Component
@OpenAPIDefinition(info = @Info(title = "Ruru Crafts API", version = "1.0.0",contact=@Contact(name = "Ruth Mwangi",email = "rwmwangi96@gmail.com"),description = "API documentation for Ruru Crafts an ecommerce site"))
@Hidden
public class RuruCraftsEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuruCraftsEcommerceApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().addSecurityItem(new SecurityRequirement().
						addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes
						("Bearer Authentication", createAPIKeyScheme()));
	}
	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer");
	}





}
