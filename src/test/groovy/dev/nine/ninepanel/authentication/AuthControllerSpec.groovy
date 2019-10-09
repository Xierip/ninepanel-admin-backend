package dev.nine.ninepanel.authentication

import dev.nine.ninepanel.authentication.domain.dto.SignInDto
import dev.nine.ninepanel.authentication.refreshtoken.dto.RefreshTokenRequestDto
import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import org.bson.types.ObjectId
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthControllerSpec extends IntegrationSpec {
  def "successful user login scenario"() {
    given: "there is a user in the system with following credentials"
      SignInDto signInDto2 = SignInDto.builder()
          .email(authenticatedUser.email)
          .password(authenticatedUser.password)
          .deviceId("x")
          .build()
    when: "i try to log in with an existent user credentials"
      ResultActions request2 = requestAsAnonymous(post(ApiLayers.SESSIONS)
          .content(objectToJson(signInDto2))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the login should be successful"
      request2.andExpect(status().isOk())
  }

  def "fail user login scenario"() {
    given: "there is no user in the system with following credentials"
      SignInDto signInDto = SignInDto
          .builder()
          .email("wrong@credentials.com")
          .password("lol123")
          .deviceId("x")
          .build()
    when: "i try to log in with wrong credentials"
      ResultActions request = requestAsAnonymous(post(ApiLayers.SESSIONS)
          .content(objectToJson(signInDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the login should fail"
      request.andExpect(status().isUnauthorized())
  }

  def "successful delete token scenario"() {
    given: "i have adminToken"
      Map<String, String> refreshTokenMap = ["refreshToken": refreshToken]

    when: "i try to delete my adminToken"
      ResultActions request = requestAsRoot(delete(ApiLayers.SESSIONS)
          .content(objectToJson(refreshTokenMap))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "the adminToken should be deleted"
      request.andExpect(status().isOk())
  }

  def "fail delete token scenario"() {
    given: "i have a non-existent adminToken"
      Map<String, String> refreshTokenMap = ["refreshToken": "lmaoImNotAToken"]

    when: "i try to delete a non-existent adminToken"
      ResultActions request = requestAsRoot(delete(ApiLayers.SESSIONS)
          .content(objectToJson(refreshTokenMap))
          .contentType(MediaType.APPLICATION_JSON_UTF8))

    then: "the adminToken should not be found"
      request.andExpect(status().isNotFound())

    when: "i try to delete a adminToken without being logged in"
      ResultActions request2 = requestAsAnonymous(delete(ApiLayers.SESSIONS)
          .content(objectToJson(refreshTokenMap))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return unauthorized"
      request2.andExpect(status().isUnauthorized())

    when: "i don't send a adminToken"
      refreshTokenMap.remove("refreshToken")
      ResultActions request3 = requestAsRoot(delete(ApiLayers.SESSIONS)
          .content(objectToJson(refreshTokenMap))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request3.andExpect(status().isBadRequest())
  }

  def "successful refresh token scenario"() {
    given: "i have a refresh adminToken in the system"
      RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto(authenticatedUser.id, refreshToken)
    when: "i send my refresh adminToken"
      ResultActions request2 = requestAsAnonymous(post("$ApiLayers.SESSIONS/refresh")
          .content(objectToJson(refreshTokenRequestDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request2.andExpect(status().isOk())
    and: "i parse new tokens from response"
      String resultString = request2.andReturn().getResponse().getContentAsString()
      Map<String, String> tokens = objectMapper.readValue(resultString, Map.class)
    when: "i try to get my data using new adminToken"
      ResultActions request3 = requestAsRoot(get("$ApiLayers.USERS/me"), tokens.get("accessToken") as String)
    then: "i should get user info"
      request3.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    and: "the user info should match with database"
      request3.andExpect(content().json(objectToJson(authenticatedUser)))
  }


  def "fail refresh token scenario"() {
    given: "i have two invalid request tokens"
      RefreshTokenRequestDto refreshTokenRequestDto = new RefreshTokenRequestDto(new ObjectId(), refreshToken)
      RefreshTokenRequestDto refreshTokenRequestDto2 = new RefreshTokenRequestDto(authenticatedUser.id, "Fv454ycc45y4cx4ccacegr")

    when: "i send my refresh adminToken with other UserId"
      ResultActions request1 = requestAsAnonymous(post("$ApiLayers.SESSIONS/refresh")
          .content(objectToJson(refreshTokenRequestDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the system should not found this adminToken"
      request1.andExpect(status().isNotFound())

    when: "i send bad refresh adminToken with my UserId"
      ResultActions request2 = requestAsAnonymous(post("$ApiLayers.SESSIONS/refresh")
          .content(objectToJson(refreshTokenRequestDto2))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the system should not found this adminToken"
      request2.andExpect(status().isNotFound())
  }


}
