package dev.nine.ninepanel.base

import com.fasterxml.jackson.databind.ObjectMapper
import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetup
import dev.nine.ninepanel.AppRunner
import dev.nine.ninepanel.authentication.domain.dto.SignInDto
import dev.nine.ninepanel.infrastructure.constant.MongoCollections
import dev.nine.ninepanel.user.domain.UserFacade
import dev.nine.ninepanel.user.domain.UserRoles
import dev.nine.ninepanel.user.domain.dto.UserCreationDto
import dev.nine.ninepanel.user.domain.dto.UserDto
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.JacksonJsonParser
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.mail.internet.MimeMessage

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TypeChecked
@SpringBootTest(classes = AppRunner)
@ActiveProfiles("test")
abstract class IntegrationSpec extends Specification {

  @Autowired
  protected WebApplicationContext webApplicationContext
  @Autowired
  protected ObjectMapper objectMapper
  @Autowired
  protected UserFacade userFacade
  @Autowired
  protected MongoTemplate mongoTemplate

  GreenMail smtpServer
  MockMvc mockMvc
  String accessToken
  String refreshToken
  UserDto authenticatedUser

  void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build()
    Map<String, String> tokens = obtainTokens()
    refreshToken = tokens.get("refreshToken")
    accessToken = tokens.get("accessToken")

    smtpServer = new GreenMail(new ServerSetup(2525, null, "smtp"))
  }

  void cleanup() {
    mongoTemplate.dropCollection(MongoCollections.RESET_PASSWORD_REQUESTS)
    mongoTemplate.dropCollection(MongoCollections.SERVICES)
    mongoTemplate.dropCollection(MongoCollections.HOSTINGS)
    mongoTemplate.dropCollection(MongoCollections.SERVICE_TYPES)
    mongoTemplate.dropCollection(MongoCollections.TOKENS)
    mongoTemplate.dropCollection(MongoCollections.USERS)
    mongoTemplate.dropCollection(MongoCollections.NOTIFICATIONS)
    mongoTemplate.dropCollection(MongoCollections.CLIENTS)

    smtpServer.stop()
  }

  String objectToJson(Object object) {
    return objectMapper.writeValueAsString(object)
  }

  Map<String, String> obtainTokens() throws Exception {

    UserCreationDto signUpDto = new UserCreationDto(
        "authuser@security.com",
        "securePass123",
        "test",
        "testCaptcha",
        UserRoles.ROOT)

    authenticatedUser = userFacade.create(signUpDto)
    authenticatedUser.setPassword(signUpDto.password)

    SignInDto signInDto = SignInDto
        .builder()
        .email(signUpDto.email)
        .password(signUpDto.password)
        .deviceId("testDevice")
        .build()

    ResultActions result = mockMvc.perform(post("/api/sessions")
        .content(objectToJson(signInDto))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

    String resultString = result.andReturn().getResponse().getContentAsString()
    JacksonJsonParser jsonParser = new JacksonJsonParser()

    return jsonParser.parseMap(resultString) as Map<String, String>
  }

  ResultActions requestAsAnonymous(MockHttpServletRequestBuilder requestBuilder) {
    return mockMvc.perform(requestBuilder)
  }

  ResultActions requestAsRoot(MockHttpServletRequestBuilder requestBuilder) {
    return mockMvc.perform(requestBuilder.header("Authorization", "Bearer ${accessToken}"))
  }

  ResultActions requestAsRoot(MockHttpServletRequestBuilder requestBuilder, String accessToken) {
    return mockMvc.perform(requestBuilder.header("Authorization", "Bearer ${accessToken}"))
  }

  List<MimeMessage> getMailMessages() {
    return smtpServer.getReceivedMessages().toList()
  }


}