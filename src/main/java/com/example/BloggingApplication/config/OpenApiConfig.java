package com.example.BloggingApplication.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "MarufHasan",
                        email = "marufhasan212@gmail.com"
                ),
                description = "OpenAPI documentation for UnitTest in SpringBoot",
                title = "Unit Test OpenApi specification",
                version = "1.0",
                termsOfService = "terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
@Configuration
public class OpenApiConfig {

}
