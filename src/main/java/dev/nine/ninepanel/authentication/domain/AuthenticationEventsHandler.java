package dev.nine.ninepanel.authentication.domain;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
class AuthenticationEventsHandler {

  private LoginFailuresService loginFailuresService;

  AuthenticationEventsHandler(LoginFailuresService loginFailuresService) {
    this.loginFailuresService = loginFailuresService;
  }

  @EventListener(AuthenticationSuccessEvent.class)
  public void onSuccess(AuthenticationSuccessEvent event) {
    loginFailuresService.clear(event.getAuthentication().getName());
  }

  @EventListener(AuthenticationFailureBadCredentialsEvent.class)
  public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
    loginFailuresService.increase(event.getAuthentication().getName());
  }
}
