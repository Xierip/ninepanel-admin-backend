package dev.nine.ninepanel.infrastructure.jackson;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacksonObjectMapperConfig {

  private static final String DATE_FORMAT      = "yyyy-MM-dd";
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> {
      builder.simpleDateFormat(DATE_TIME_FORMAT);
      builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
      builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
    };
  }
}
