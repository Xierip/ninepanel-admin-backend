package dev.nine.ninepanel.user

import dev.nine.ninepanel.authentication.domain.dto.SignInDto
import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import dev.nine.ninepanel.token.domain.TokenFacade
import dev.nine.ninepanel.user.changepassword.dto.ChangePasswordDto
import dev.nine.ninepanel.user.domain.SampleUsers
import dev.nine.ninepanel.user.domain.dto.UserCreationDto
import dev.nine.ninepanel.user.domain.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class UserControllerSpec extends IntegrationSpec implements SampleUsers {

  @Autowired
  TokenFacade tokenFacade

  void setup() {
    smtpServer.start()
  }

  void cleanup() {
    smtpServer.stop()
  }

  def "successful user info access scenario"() {
    given: "i am a user in the system"
    when: "i request user info with access token"
      ResultActions request2 = requestAsRoot(get("$ApiLayers.USERS/me"))
    then: "i should get user info"
      request2.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    and: "the user info should match with database"
      request2.andExpect(content().json(objectToJson(authenticatedUser)))
  }

  def "fail user info access scenario"() {
    when: "i request user info without access token"
      ResultActions request = requestAsAnonymous(get("$ApiLayers.USERS/me"))
    then: "i should get a 401 unauthorized"
      request.andExpect(status().isUnauthorized())
  }

  def "successful user password change scenario"() {
    given: "i am a user in the system with valid password change data"
      String newPassword = authenticatedUser.password + "new"
      ChangePasswordDto changePasswordDto = new ChangePasswordDto(authenticatedUser.password, newPassword)

    when: "i send a request to update password"
      ResultActions request = requestAsRoot(post("$ApiLayers.USERS/change-password")
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
      ResultActions request = requestAsRoot(post("$ApiLayers.USERS/change-password")
          .content(objectToJson(changePasswordDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "the request should fail"
      request.andExpect(status().isUnprocessableEntity())
  }

  def "successful user creation scenario"() {
    given: "i have valid user creation data"
    when: "i register a new user"
      ResultActions request = requestAsRoot(post(ApiLayers.USERS)
          .content(objectToJson(sampleSignUpDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the user should be registered"
      request.andExpect(status().isOk())

    and: "i should be able to log in as the created user"
      logIn(sampleSignUpDto.email, sampleSignUpDto.password)
  }

  def "fail user creation scenario"() {
    given: "i have invalid user register data"
      UserCreationDto signUpDto = sampleSignUpDto
      signUpDto.password = "bad"
    when: "i try to register a new user with invalid data"
      ResultActions request = requestAsRoot(post(ApiLayers.USERS)
          .content(objectToJson(signUpDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())
  }

  def "success other user password change scenario"() {
    given: "system has other user"
      UserDto otherUser = userFacade.create(sampleSignUpDto1)
    when: "i change his password"
      ResultActions request = requestAsRoot(post("${ApiLayers.USERS}/${otherUser.id.toHexString()}/change-password")
          .content(objectToJson(Map.of("password", "otherPass")))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    and: "the password should be changed"
      request.andExpect(status().isNoContent())
    then: "he should be able to log in with new password"
      logIn(otherUser.email, "otherPass")

  }

  def "success user delete scenario"() {
    given: "system has other user"
      UserDto otherUser = userFacade.create(sampleSignUpDto1)
    when: "i delete this user by id"
      ResultActions request = requestAsRoot(delete("${ApiLayers.USERS}/${otherUser.id.toHexString()}"))
    and: "the user should be deleted"
      request.andExpect(status().isNoContent())
    then: "i shouldn't be able to fetch this user"
      requestAsRoot(get("${ApiLayers.USERS}/${otherUser.id.toHexString()}"))
          .andExpect(status().isNotFound())
  }

  def "success fetch all users scenario"() {
    given: "system has two users"
      UserDto otherUser = userFacade.create(sampleSignUpDto1)
    when: "i ask system for users"
      ResultActions request = requestAsRoot(get(ApiLayers.USERS))
    then: "i should see one"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(2)))
  }

  def "success update user scenario"() {
    given: "system has other user"
      UserDto otherUser = userFacade.create(sampleSignUpDto1)
    when: "i send updated user"
      otherUser.surname = "pickles"
      ResultActions request = requestAsRoot(put("${ApiLayers.USERS}/${otherUser.id.toHexString()}")
          .content(objectToJson(otherUser))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isOk())
    and: "i fetch this user"
      request = requestAsRoot(get("${ApiLayers.USERS}/${otherUser.id.toHexString()}"))
          .andExpect(status().isOk())
    then: "users should be equals"
      request.andExpect(content().json(objectToJson(otherUser)))
  }

  private void logIn(String email, String password) {
    SignInDto sign = SignInDto.builder().email(email).password(password).deviceId("someDevice").build()
    ResultActions logInRequest = requestAsAnonymous(post(ApiLayers.SESSIONS)
        .content(objectToJson(sign))
        .contentType(MediaType.APPLICATION_JSON_UTF8))
    logInRequest.andExpect(status().isOk())
  }

}

