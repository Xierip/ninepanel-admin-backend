package dev.nine.ninepanel.notifications

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.clients.ClientsData
import dev.nine.ninepanel.clients.domain.dto.ClientDto
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import org.bson.types.ObjectId
import org.springframework.test.web.servlet.ResultActions

import java.time.LocalDateTime

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class NotificationControllerSpec extends IntegrationSpec implements NotificationData, ClientsData {

  def "successful notification fetch scenario"() {
    given: "there are 1 user and 3 notifications in the system"
      ClientDto clientDto = setUpClient("client1@test.com")
      setupLinkNotification(clientDto.id, "Test notif", "https://link.to", false, clientDto.notificationsReadAt)
      setupTextNotification(clientDto.id, "Test notif2", clientDto.notificationsReadAt)
      setupTextNotification(new ObjectId(), "Test notif3", LocalDateTime.now())
    when: "i access the notifications endpoint"
      ResultActions request = requestAsRoot(get("${ApiLayers.NOTIFICATIONS}/?clientId=${clientDto.id.toHexString()}"))
    then: "i should get a page with 2 notifications"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(2)))
  }

  def "fail notification fetch scenario"() {
    given: "there are 1 user and 1 notification in the system"
      ClientDto clientDto = setUpClient("client1@test.com")
      setupRandomNotification(new ObjectId(), "Test notif3")
    when: "i access the notifications endpoint"
      ResultActions request = requestAsRoot(get("${ApiLayers.NOTIFICATIONS}/?clientId=${clientDto.id.toHexString()}"))
    then: "the request should be okay"
      request.andExpect(status().isOk())
    and: "i cannot se any notification"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content").isEmpty())
  }
}