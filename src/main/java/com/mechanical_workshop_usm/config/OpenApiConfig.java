package com.mechanical_workshop_usm.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.tags.Tag;


import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Mechanical Workshop API")
                .description("Rest API para el Taller Mecánico USM")
            ).tags(List.of(
                new Tag()
                    .name("Check In")
                    .description("Getion de registros de entrada"),

                new Tag()
                    .name("Work Order")
                    .description("gestion de work order con un check in base")
            ));
    }
}
