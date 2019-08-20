package dev.nine.ninepanel.message

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import dev.nine.ninepanel.message.domain.MessageFacade
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class MessageControllerSpec extends IntegrationSpec implements MessageData {

  @Autowired
  MessageFacade messageFacade

  void setup() {
    setupMessages()
  }

  def "successful messages access scenario"() {
    given: "there are 2 messages in the system for a user"
      messageFacade.add(validMessageCreationDto)
      messageFacade.add(validMessageCreationDto)
    when: "i ask for the messages for this user"
      ResultActions request = requestAsUser(get(ApiLayers.MESSAGES)
          .param("userId", validMessageCreationDto.getRecipientId().toHexString()))
    then: "the request should return 2 messages"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(2)))
  }

  def "fail messages access scenario"() {
    given: "there are 2 messages in the system"
      messageFacade.add(validMessageCreationDto)
      messageFacade.add(validMessageCreationDto)
    when: "i ask for the messages as anonymous"
      ResultActions request = requestAsAnonymous(get(ApiLayers.MESSAGES))
    then: "the request should return unauthorized"
      request
          .andExpect(status().isUnauthorized())
  }

  def "successful message add scenario"() {
    given: "i have valid message creation data"
    when: "i try to add a message"
      ResultActions request = requestAsUser(post(ApiLayers.MESSAGES)
          .content(objectToJson(validMessageCreationDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the message should be added"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.body", containsString(validMessageCreationDto.body)))
  }

  def "fail message add scenario"() {
    given: "i have invalid message creation data"
    when: "i try to add a message for a user that doesnt exist"
      ResultActions request = requestAsUser(post(ApiLayers.MESSAGES)
          .content(objectToJson(noUserMessageCreationDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request
          .andExpect(status().isNotFound())
  }

  def "successful read all unread messages"() {
    given: "there is a message in the system"
      ObjectId messageId = setupReceivedMessage(authenticatedUser.id)
    when: "i mark all the messages as read"
      ResultActions request = requestAsUser(post(ApiLayers.MESSAGES + "/mark-as-read")
          .param("userId", authenticatedUser.id.toHexString()))
    and: "i ask for the message"
      ResultActions request2 = requestAsUser(get(ApiLayers.MESSAGES)
          .param("userId", authenticatedUser.id.toHexString()))
    then: "the request should return a message that is read"
      request.andExpect(status().isNoContent())
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content[?(@.id == '${messageId}')].read", equalTo([true])))
  }

}
