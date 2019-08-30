package dev.nine.ninepanel.email.domain;

import javax.mail.MessagingException;

public class EmailFacade {

  private final EmailCreationService emailCreationService;
  private final EmailSender          emailSender;

  EmailFacade(EmailCreationService emailCreationService, EmailSender emailSender) {
    this.emailCreationService = emailCreationService;
    this.emailSender = emailSender;
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
