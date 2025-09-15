package com.dbiServer.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
  @Bean
  public WebMvcConfigurer corsConfigs() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("https://dbi-client.vercel.app", "https://dog-breed-identifier.vercel.app",
                "http://localhost:5173")
            .allowedMethods("GET", "POST")
            .allowedHeaders("*")
            .allowCredentials(true);
      }
    };
  }
}
