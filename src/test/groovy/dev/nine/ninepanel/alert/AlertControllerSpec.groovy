package dev.nine.ninepanel.alert

import dev.nine.ninepanel.alert.domain.AlertFacade
import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AlertControllerSpec extends IntegrationSpec implements AlertData {

  @Autowired
  AlertFacade alertFacade

  def "successful fetch alerts scenario"() {
    given: "there is a alert in the system"
      alertFacade.add(validAlertDto)
    when: "i access the alert endpoint"
      ResultActions request = requestAsRoot(get(ApiLayers.ALERTS))
    then: "the request should be okay"
      request.andExpect(status().isOk())
    and: "i should get a list with 1 alert"
      request.andExpect(jsonPath("\$", hasSize(1)))
  }

  def "fail fetch alerts scenario"() {
    when: "i try to access alerts and im not logged in"
      ResultActions request = requestAsAnonymous(get(ApiLayers.ALERTS))
    then: "the request should fail"
      request.andExpect(status().isUnauthorized())
  }

  def "successful add alert scenario"() {
    given: "there are no alerts in the system"
    when: "i post valid alert data to add route"
      ResultActions request = requestAsRoot(post(ApiLayers.ALERTS)
          .content(objectToJson(validAlertDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    when: "i fetch the alerts"
      ResultActions request2 = requestAsRoot(get(ApiLayers.ALERTS))
    then: "there should be one alert in the system"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(1)))
  }

  def "fail add alert scenario"() {
    given: "i have invalid alert creation date"
    when: "i post it to add route"
      ResultActions request = requestAsRoot(post(ApiLayers.ALERTS)
          .content(objectToJson(invalidAlertDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())
  }

  def "successful delete alert scenario"() {
    given: "there is a alert in the system"
      String id = alertFacade.add(validAlertDto).id
    when: "i try to delete the alert"
      ResultActions request = requestAsRoot(delete("$ApiLayers.ALERTS/${id}"))
    then: "the request should result no content"
      request.andExpect(status().isNoContent())
    when: "i fetch the alerts"
      ResultActions request2 = requestAsRoot(get(ApiLayers.ALERTS))
    then: "there should be zero alert in the system"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(0)))
  }

  def "fail delete alert scenario"() {
    when: "i try to delete a alert that doesn't exist"
      ResultActions request = requestAsRoot(delete("$ApiLayers.ALERTS/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request.andExpect(status().isNotFound())
  }

}
