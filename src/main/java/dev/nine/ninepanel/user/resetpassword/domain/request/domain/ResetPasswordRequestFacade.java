package dev.nine.ninepanel.user.resetpassword.domain.request.domain;

import dev.nine.ninepanel.user.resetpassword.domain.request.domain.exception.ResetPasswordAlreadyRequestedException;
import java.time.LocalDateTime;

public class ResetPasswordRequestFacade {

  private final static int                            REQUEST_DEBOUNCE_MINUTES = 30;
  private final        ResetPasswordRequestRepository resetPasswordRequestRepository;

  public ResetPasswordRequestFacade(
      ResetPasswordRequestRepository resetPasswordRequestRepository) {
    this.resetPasswordRequestRepository = resetPasswordRequestRepository;
  }

  public void addRequest(String email) {
    /* this should be moved to a service i guess */
    if (resetPasswordRequestRepository.existsByEmail(email)) {
      throw new ResetPasswordAlreadyRequestedException(email);
    }

    ResetPasswordRequest request = new ResetPasswordRequest(null, email, LocalDateTime.now().plusMinutes(REQUEST_DEBOUNCE_MINUTES));

    resetPasswordRequestRepository.save(request);
  }
}
