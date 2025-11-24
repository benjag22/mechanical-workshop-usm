package com.mechanical_workshop_usm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AppCorsProperties appCorsProperties;

    public WebConfig( AppCorsProperties appCorsProperties) {
        this.appCorsProperties = appCorsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
            .allowedOriginPatterns(appCorsProperties.getAllowedOrigins().toArray(String[]::new))
            .allowedMethods(appCorsProperties.getAllowedMethods().toArray(String[]::new))
            .allowedHeaders(appCorsProperties.getAllowedHeaders().toArray(String[]::new))
            .allowCredentials(appCorsProperties.getAllowCredentials());
    }
}

