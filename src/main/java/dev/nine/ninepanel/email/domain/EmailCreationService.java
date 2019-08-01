package dev.nine.ninepanel.email.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

class EmailCreationService {

  private final TemplateEngine templateEngine;

  EmailCreationService(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  private String createTemplateAsString(String templatePath, Map<String, String> contextVariablesMap) {
    Objects.requireNonNull(templatePath, "Template path cannot be null");
    Context context = new Context();

    contextVariablesMap.forEach((key, value) -> context.setVariable(key, value));

    return this.templateEngine.process(templatePath, context);
  }


  public String createPasswordChangeTemplate() {
    return createTemplateAsString("change-password-email.html", Collections.emptyMap());
  }
}
