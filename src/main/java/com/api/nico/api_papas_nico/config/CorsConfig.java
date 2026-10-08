package com.api.nico.api_papas_nico.config;

import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private final String[] allowedOrigins;

    public CorsConfig(
            @Value("${CORS_ALLOWED_ORIGINS:}") String frontendOrigins
    ) {
        allowedOrigins = Stream.concat(
                        Stream.of(
                                "http://localhost:4200",
                                "https://api-papas-nico.onrender.com"
                        ),
                        Arrays.stream(frontendOrigins.split(","))
                )
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .map(origin -> origin.replaceAll("/+$", ""))
                .distinct()
                .toArray(String[]::new);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(
                        "GET", "POST", "PUT", "DELETE", "OPTIONS"
                )
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}