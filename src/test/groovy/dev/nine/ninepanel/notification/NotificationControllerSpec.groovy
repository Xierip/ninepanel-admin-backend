package dev.nine.ninepanel.notification;

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.notification.domain.NotificationFacade
import dev.nine.ninepanel.notification.domain.dto.NotificationDto
import dev.nine.ninepanel.notification.domain.dto.NotificationType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions

import javax.xml.transform.Result

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

public class NotificationControllerSpec extends IntegrationSpec {

  @Autowired
  NotificationFacade notificationFacade

  def "successful notification fetch scenario"() {
    given: "there is a notification in the system"
      NotificationDto dto = new NotificationDto(null, "Test notification", NotificationType.ALERT)
      notificationFacade.addNotification(dto)
    when: "i access the notification endpoint"
      ResultActions request = requestAsUser(get("/api/notifications"))
    then: "the request should be okay"
      request.andExpect(status().isOk())
    and: "i should get a list with 1 notification"
      String resultString = request.andReturn().getResponse().getContentAsString()
      List<NotificationDto> list = objectMapper.readValue(resultString, List.class)
      list.size() == 1
  }

  def "fail notification fetch scenario"() {
    when: "i try to access notifications and im not logged in"
      ResultActions request = requestAsAnonymous(get("/api/notifications"))
    then: "the request should fail"
      request.andExpect(status().isUnauthorized())
  }

}
