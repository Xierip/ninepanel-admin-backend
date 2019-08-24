package dev.nine.ninepanel.message

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import dev.nine.ninepanel.message.domain.MessageFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
      messageFacade.addAdminMessage(validMessageCreationDto.body, clientId)
      messageFacade.addClientMessage(validMessageCreationDto.body, clientId)
    when: "i ask for the messages for this user"
      ResultActions request = requestAsUser(get(ApiLayers.MESSAGES)
          .param("userId", clientId.toHexString()))
    then: "the request should return 2 messages"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(2)))
  }

  def "fail messages access scenario"() {
    given: "there are 2 messages in the system"
      messageFacade.addAdminMessage(validMessageCreationDto.body, clientId)
      messageFacade.addClientMessage(validMessageCreationDto.body, clientId)
    when: "i ask for the messages as anonymous"
      ResultActions request = requestAsAnonymous(get(ApiLayers.MESSAGES))
    then: "the request should return unauthorized"
      request
          .andExpect(status().isUnauthorized())
  }

}
