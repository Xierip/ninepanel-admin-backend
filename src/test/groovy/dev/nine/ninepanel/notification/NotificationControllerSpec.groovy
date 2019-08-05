package dev.nine.ninepanel.notification;

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.notification.domain.NotificationFacade
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class NotificationControllerSpec extends IntegrationSpec implements NotificationData {

  @Autowired
  NotificationFacade notificationFacade

  def "successful fetch notification scenario"() {
    given: "there is a notification in the system"
      notificationFacade.add(validNotificationDto)
    when: "i access the notification endpoint"
      ResultActions request = requestAsUser(get("/api/notifications"))
    then: "the request should be okay"
      request.andExpect(status().isOk())
    and: "i should get a list with 1 notification"
      request.andExpect(jsonPath("\$", hasSize(1)))
  }

  def "fail fetch notification scenario"() {
    when: "i try to access notifications and im not logged in"
      ResultActions request = requestAsAnonymous(get("/api/notifications"))
    then: "the request should fail"
      request.andExpect(status().isUnauthorized())
  }

  def "successful add notification scenario"() {
    given: "there are no notifications in the system"
    when: "i post valid notification data to add route"
      ResultActions request = requestAsUser(post("/api/notifications")
          .content(objectToJson(validNotificationDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    when: "i fetch the notifications"
      ResultActions request2 = requestAsUser(get("/api/notifications"))
    then: "there should be one notification in the system"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(1)))
  }

  def "fail add notification scenario"() {
    given: "i have invalid notification creation date"
    when: "i post it to add route"
      ResultActions request = requestAsUser(post("/api/notifications")
          .content(objectToJson(invalidNotificationDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())
  }

  def "successful delete notification scenario"() {
    given: "there is a notification in the system"
      String id = notificationFacade.add(validNotificationDto).id
    when: "i try to delete the notification"
      ResultActions request = requestAsUser(delete("/api/notifications/${id}"))
    then: "the request should result no content"
      request.andExpect(status().isNoContent())
    when: "i fetch the notifications"
      ResultActions request2 = requestAsUser(get("/api/notifications"))
    then: "there should be zero notification in the system"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(0)))
  }

  def "fail delete notification scenario"() {
    when: "i try to delete a notification that doesn't exist"
      ResultActions request = requestAsUser(delete("/api/notifications/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request.andExpect(status().isNotFound())
  }

}
