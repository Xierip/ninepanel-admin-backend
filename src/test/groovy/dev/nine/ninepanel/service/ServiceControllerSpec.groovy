package dev.nine.ninepanel.service

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import dev.nine.ninepanel.service.domain.ServiceFacade
import dev.nine.ninepanel.service.domain.dto.ServiceDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ServiceControllerSpec extends IntegrationSpec implements ServiceData {
  @Autowired
  ServiceFacade serviceFacade

  void setup() {
    setupServices()
  }

  def "successful services list access scenario"() {
    given: "system has two services"
      serviceFacade.add(validServiceDto1)
      serviceFacade.add(validServiceDto2)

    when: "i ask system for services"
      ResultActions request = requestAsUser(get(ApiLayers.SERVICES))

    then: "i see all services"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(2)))
  }

  def "fail services list access scenario"() {
    given: "system has two services"
      serviceFacade.add(validServiceDto1)
      serviceFacade.add(validServiceDto2)

    when: "i ask system for services as guest"
      ResultActions request = requestAsAnonymous(get(ApiLayers.SERVICES))

    then: "i cannot see any service"
      request
          .andExpect(status().isUnauthorized())
  }

  def "successful service access scenario"() {
    given: "system has one service"
      ServiceDto serviceDto = serviceFacade.add(validServiceDto1)

    when: "i ask system for specific service"
      ResultActions request = requestAsUser(get(ApiLayers.SERVICES + "/${serviceDto.id.toHexString()}"))

    then: "i see all my services"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(serviceDto)))
  }

  def "fail service access scenario"() {
    given: "system has one service"
      ServiceDto serviceDto = serviceFacade.add(validServiceDto1)

    when: "i ask system for specific service"
      ResultActions request = requestAsAnonymous(get(ApiLayers.SERVICES + "/${serviceDto.id.toHexString()}"))

    then: "i cannot see any service"
      request
          .andExpect(status().isUnauthorized())
  }

  def "successful service add scenario"() {
    given: "there are no services in the system"
    when: "i post the service data to add route"
      ResultActions request = requestAsUser(post(ApiLayers.SERVICES)
          .content(objectToJson(validServiceDto1))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    when: "i fetch all services"
      ResultActions request2 = requestAsUser(get(ApiLayers.SERVICES))
    then: "there should be one service"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(1)))
  }

  def "fail service add scenario"() {
    when: "i post invalid service data to add route"
      ResultActions request = requestAsUser(post(ApiLayers.SERVICES)
          .content(objectToJson(invalidServiceDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())

    when: "i post service data with nonexistent client id"
      ResultActions request2 = requestAsUser(post(ApiLayers.SERVICES)
          .content(objectToJson(noClientServiceDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful service update scenario"() {

  }

  def "fail service update scenario"() {

  }

  def "successful service delete scenario"() {

  }

  def "fail service delete scenario"() {

  }


}