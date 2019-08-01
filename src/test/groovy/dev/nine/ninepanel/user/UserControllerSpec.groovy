package dev.nine.ninepanel.user

import dev.nine.ninepanel.authentication.domain.dto.SignInDto
import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.token.domain.TokenFacade
import dev.nine.ninepanel.user.changepassword.dto.ChangePasswordDto
import dev.nine.ninepanel.user.domain.SampleUsers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

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

  private void logIn(String email, String password) {
    SignInDto sign = SignInDto.builder().email(email).password(password).deviceId("someDevice").build()
    ResultActions logInRequest = requestAsAnonymous(post("/api/sessions")
        .content(objectToJson(sign))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
    logInRequest.andExpect(status().isOk())
  }

}

