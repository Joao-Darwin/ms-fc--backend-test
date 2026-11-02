package com.fcbackend.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI activationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tweet Service API")
                        .description("""
                                REST API for managing user's tweet.
                                Allows clients to create tweets and
                                retrieve their current tweets.
                                """)
                        .version("v1")
                        .contact(new Contact()
                                .name("Tweet Service Team")));
    }
}
