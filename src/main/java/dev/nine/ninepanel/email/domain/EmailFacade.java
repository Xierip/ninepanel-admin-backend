package dev.nine.ninepanel.email.domain;

import dev.nine.ninepanel.user.domain.dto.UserDto;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;

public class EmailFacade {

  private final EmailCreationService emailCreationService;
  private final EmailSender          emailSender;

  @Value("${frontend_url}")
  private String frontendUrl;

  EmailFacade(EmailCreationService emailCreationService, EmailSender emailSender) {
    this.emailCreationService = emailCreationService;
    this.emailSender = emailSender;
  }

  public void sendPasswordResetEmail(UserDto userDto, String token) {
    try {
      String link = frontendUrl + "/reset-password?token=" + token;
      String username = userDto.getName() + " " + userDto.getSurname();
      String emailTemplate = emailCreationService.createResetPasswordTemplate(link, username);
      String subject = "Password Reset";

      emailSender.sendMessage(userDto.getEmail(), subject, emailTemplate);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public void sendPasswordChangeEmail(String email) {
    try {
      String emailTemplate = emailCreationService.createPasswordChangeTemplate();
      String subject = "Password Change";

      emailSender.sendMessage(email, subject, emailTemplate);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }


}
