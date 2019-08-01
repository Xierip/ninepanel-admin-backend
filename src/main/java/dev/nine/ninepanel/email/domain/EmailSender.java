package dev.nine.ninepanel.email.domain;

import com.google.common.base.Charsets;
import java.util.Objects;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

class EmailSender {

  private final static String         FROM = "system@nine.dev";
  private final        JavaMailSender emailSender;

  EmailSender(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Async
  public void sendMessage(String to, String subject, String text) throws MessagingException {
    Objects.requireNonNull(to, "Destination cannot be null");
    Objects.requireNonNull(subject, "Subject cannot be null");
    Objects.requireNonNull(text, "Body cannot be null");

    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, Charsets.UTF_8.name());

    helper.setFrom(FROM);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, true);

    emailSender.send(message);
  }

}
