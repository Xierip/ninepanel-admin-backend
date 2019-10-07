package dev.nine.ninepanel.infrastructure.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebMvcConfiguration implements WebMvcConfigurer {

  @Value("${allowedOrigins}")
  private String[] allowedOrigins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(allowedOrigins)
        .allowCredentials(true)
        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
        .allowedHeaders("Authorization", "Cache-Control", "Content-Type")
        .exposedHeaders("Location")
        .maxAge(3600);
  }

  @Bean
  public CommonsRequestLoggingFilter requestLoggingFilter() {
    CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
    loggingFilter.setIncludeClientInfo(true);
    loggingFilter.setIncludeQueryString(true);
    loggingFilter.setIncludePayload(true);
    loggingFilter.setIncludeHeaders(true);
    return loggingFilter;
  }

}
