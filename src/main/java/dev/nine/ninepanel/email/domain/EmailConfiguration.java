package dev.nine.ninepanel.email.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.thymeleaf.TemplateEngine;


@Configuration
@EnableAsync
class EmailConfiguration {

  @Bean
  EmailFacade emailFacade(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
    EmailCreationService creator = new EmailCreationService(templateEngine);
    EmailSender sender = new EmailSender(javaMailSender);

    return new EmailFacade(creator, sender);
  }

}
