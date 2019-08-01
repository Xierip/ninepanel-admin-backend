package dev.nine.ninepanel.user

import dev.nine.ninepanel.authentication.domain.dto.SignInDto
import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.token.domain.TokenFacade
import dev.nine.ninepanel.token.domain.TokenType
import dev.nine.ninepanel.token.domain.dto.TokenDto
import dev.nine.ninepanel.user.changepassword.dto.ChangePasswordDto
import dev.nine.ninepanel.user.companydetails.CompanyDetails
import dev.nine.ninepanel.user.domain.SampleUsers
import dev.nine.ninepanel.user.domain.dto.SignUpDto
import dev.nine.ninepanel.user.domain.dto.UserUpdateDto
import dev.nine.ninepanel.user.resetpassword.domain.dto.ForgotPasswordDto
import dev.nine.ninepanel.user.resetpassword.domain.dto.ResetPasswordDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import javax.mail.internet.MimeMessage

import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class UserControllerSpec extends IntegrationSpec implements SampleUsers {

  @Autowired
  TokenFacade tokenFacade

  def "successful user info access scenario"() {
    given: "i am a user in the system"
    when: "i request user info with access token"
      ResultActions request2 = requestAsUser(get("/api/users/me"))
    then: "i should get user info"
      request2.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    and: "the user info should match with database"
      request2.andExpect(content().json(objectToJson(authenticatedUser)))
  }

  def "fail user info access scenario"() {
    when: "i request user info without access token"
      ResultActions request = requestAsAnonymous(get("/api/users/me"))
    then: "i should get a 401 unauthorized"
      request.andExpect(status().isUnauthorized())
  }

  def "successful user forgot password scenario"() {
    given: "i am a user in the system and have valid password reset data"
      ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto(authenticatedUser.email)

    when: "i forgot my password and request a reset"
      ResultActions request = requestAsAnonymous(post("/api/users/forgot-password")
          .content(objectToJson(forgotPasswordDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be processed"
      request.andExpect(status().isNoContent())
    and: "i should get a password reset token"
      Optional<TokenDto> passwordResetToken = tokenFacade.getTokenByUserIdAndTokenType(authenticatedUser.id, TokenType.PASSWORD_RESET_TOKEN)
      passwordResetToken.isPresent()
    and: "i should get an email"
      List<MimeMessage> messages = getMailMessages()
      messages.size() == 1

    when: "i send the token and new password to the password reset route"
      String newPassword = authenticatedUser.password + "abc"
      ResetPasswordDto resetPasswordDto = new ResetPasswordDto(passwordResetToken.get().body, newPassword)

      ResultActions request2 = requestAsAnonymous(post("/api/users/reset-password")
          .content(objectToJson(resetPasswordDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "my password should be changed"
      request2.andExpect(status().isNoContent())
    and: "i should be able to log in using the new password"
      logIn(authenticatedUser.email, newPassword)

    when: "i look for the used token"
      Optional<TokenDto> passwordResetToken2 = tokenFacade.getTokenByUserIdAndTokenType(authenticatedUser.id, TokenType.PASSWORD_RESET_TOKEN)
    then: "the token should be gone"
      passwordResetToken2.isEmpty()
  }

  def "fail user forgot password scenario"() {
    given: "i have invalid password reset data"
      ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto("idont@exist.com")
      String newPassword = authenticatedUser.password + "abc"
      ResetPasswordDto resetPasswordDto = new ResetPasswordDto("lmao", newPassword)

    when: "i request a password reset for a non-existent user email"
      ResultActions request = requestAsAnonymous(post("/api/users/forgot-password")
          .content(objectToJson(forgotPasswordDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isNoContent())

    when: "i send a wrong token to the password reset route"
      ResultActions request2 = requestAsAnonymous(post("/api/users/reset-password")
          .content(objectToJson(resetPasswordDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request2.andExpect(status().isNotFound())

  }

  def "successful user password change scenario"() {
    given: "i am a user in the system with valid password change data"
      String newPassword = authenticatedUser.password + "new"
      ChangePasswordDto changePasswordDto = new ChangePasswordDto(authenticatedUser.password, newPassword)

    when: "i send a request to update password"
      ResultActions request = requestAsUser(post("/api/users/change-password")
          .content(objectToJson(changePasswordDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "the password should be updated"
      request.andExpect(status().isOk())

    and: "i should be able to log in using the new password"
      logIn(authenticatedUser.email, newPassword)
  }

  def "fail user password change scenario"() {
    given: "i am a user in the system with invalid change password data"
      ChangePasswordDto changePasswordDto = new ChangePasswordDto("wrongPassword", "newPassword")

    when: "i send a request to update password with a wrong user password"
      ResultActions request = requestAsUser(post("/api/users/change-password")
          .content(objectToJson(changePasswordDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "the request should fail"
      request.andExpect(status().isUnprocessableEntity())
  }

  def "successful user update scenario"() {
    given: "i am a user in the system with valid update data"
      String newName = authenticatedUser.name + "new"
      UserUpdateDto userUpdateDto = new UserUpdateDto(authenticatedUser.password,
          newName,
          authenticatedUser.surname,
          authenticatedUser.phoneNumber,
          authenticatedUser.addressDetails,
          authenticatedUser.companyDetails)
    when: "i update the authenticated user"
      ResultActions request = requestAsUser(put("/api/users/me")
          .content(objectToJson(userUpdateDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "user data should be updated"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("name", is(newName)))
  }

  def "fail user update scenario"() {
    given: "i am a user (that is not a company type) in the system with invalid update data"
      UserUpdateDto userUpdateDto = new UserUpdateDto("wrongPassword",
          authenticatedUser.name,
          authenticatedUser.surname,
          authenticatedUser.phoneNumber,
          authenticatedUser.addressDetails,
          authenticatedUser.companyDetails)
      userUpdateDto.companyDetails = new CompanyDetails("test", " 0000000000")
    when: "i update the authenticated user and send incorrect password"
      ResultActions request = requestAsUser(put("/api/users/me")
          .content(objectToJson(userUpdateDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "the request should fail"
      request
          .andExpect(status().isUnprocessableEntity())

    when: "i update the authenticated user with additional company field"
      ResultActions request2 = requestAsUser(put("/api/users/me")
          .content(objectToJson(userUpdateDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request2
          .andExpect(status().isUnprocessableEntity())
  }

  def "successful user register scenario"() {
    given: "i have valid user register data"
    when: "i register a new user"
      ResultActions request = requestAsAnonymous(post("/api/users/register")
          .content(objectToJson(sampleSignUpDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the user should be registered"
      request.andExpect(status().isOk())

    and: "i should be able to log in as the created user"
      logIn(sampleSignUpDto.email, sampleSignUpDto.password)
  }

  def "fail user register scenario"() {
    given: "i have invalid user register data"
      SignUpDto signUpDto = sampleSignUpDto
      signUpDto.password = "bad"
    when: "i try to register a new user with invalid data"
      ResultActions request = requestAsAnonymous(post("/api/users/register")
          .content(objectToJson(signUpDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())
  }

  private void logIn(String email, String password) {
    SignInDto sign = SignInDto.builder().email(email).password(password).deviceId("someDevice").build()
    ResultActions logInRequest = requestAsAnonymous(post("/api/sessions")
        .content(objectToJson(sign))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
    logInRequest.andExpect(status().isOk())
  }

}

