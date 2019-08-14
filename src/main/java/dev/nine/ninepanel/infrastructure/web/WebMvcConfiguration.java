package dev.nine.ninepanel.infrastructure.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

  @Value("${frontend_url}")
  private String frontendUrl;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(frontendUrl)
        .allowCredentials(true)
        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
        .exposedHeaders("Location")
        .maxAge(3600);
  }

}
