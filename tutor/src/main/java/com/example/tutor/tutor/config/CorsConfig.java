package com.example.tutor.tutor.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Applies CORS settings to all endpoints
                .allowedOriginPatterns("http://localhost:5173") // Allows requests from your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specifies allowed HTTP methods
                .allowedHeaders("*") // Allows all headers
                .allowCredentials(true) // Supports cookies or other credentials
                .maxAge(3600); // Sets the cache duration (in seconds) for pre-flight requests
    }
}
