package com.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springdoc.core.utils.Constants.DEFAULT_SERVER_DESCRIPTION;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.request-server}")
    private String SERVER_BASE_URL;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(List.of(new Server().url(SERVER_BASE_URL).description(DEFAULT_SERVER_DESCRIPTION)))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Jedero API")
                .description("Jedero API 문서")
                .version("1.0.0");
    }

    static {
        var schema = new Schema<LocalTime>();
        schema.example(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))).type("string");
        SpringDocUtils.getConfig().replaceWithSchema(LocalTime.class, schema);
    }
}
