package dev.nine.ninepanel.service

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import dev.nine.ninepanel.service.domain.ServiceFacade
import dev.nine.ninepanel.service.domain.dto.ServiceDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
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
      ResultActions request = requestAsROOT(get(ApiLayers.SERVICES))

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
      ResultActions request = requestAsROOT(get(ApiLayers.SERVICES + "/${serviceDto.id}"))

    then: "i see all my services"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(serviceDto)))
  }

  def "fail service access scenario"() {
    given: "system has one service"
      ServiceDto serviceDto = serviceFacade.add(validServiceDto1)

    when: "i ask system for specific service"
      ResultActions request = requestAsAnonymous(get(ApiLayers.SERVICES + "/${serviceDto.id}"))

    then: "i cannot see any service"
      request
          .andExpect(status().isUnauthorized())

    when: "i ask for a service that doesn't exist"
      ResultActions request2 = requestAsROOT(get(ApiLayers.SERVICES + "/${new ObjectId()}"))

    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful service add scenario"() {
    given: "there are no services in the system"
    when: "i post the service data to add route"
      ResultActions request = requestAsROOT(post(ApiLayers.SERVICES)
          .content(objectToJson(validServiceDto1))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    when: "i fetch all services"
      ResultActions request2 = requestAsROOT(get(ApiLayers.SERVICES))
    then: "there should be one service"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(1)))
  }

  def "fail service add scenario"() {
    when: "i post invalid service data to add route"
      ResultActions request = requestAsROOT(post(ApiLayers.SERVICES)
          .content(objectToJson(invalidServiceDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())

    when: "i post service data with nonexistent client id"
      ResultActions request2 = requestAsROOT(post(ApiLayers.SERVICES)
          .content(objectToJson(noClientServiceDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful service update scenario"() {
    given: "there is a service in the system"
      ServiceDto serviceDto = serviceFacade.add(validServiceDto1)
    when: "I update the service with valid data"
      serviceDto.setDescription("newDescription")
      ResultActions request = requestAsROOT(put(ApiLayers.SERVICES + "/${serviceDto.id}")
          .content(objectToJson(serviceDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    and: "the service should be updated"
      request.andExpect(content().json(objectToJson(serviceDto)))
  }

  def "fail service update scenario"() {
    given: "there are two services in the system"
      ServiceDto serviceDto = serviceFacade.add(validServiceDto1)
      ServiceDto serviceDto2 = serviceFacade.add(validServiceDto2)

    when: "I update first service with invalid data"
      serviceDto.setDescription(null)
      ResultActions request = requestAsROOT(put(ApiLayers.SERVICES + "/${serviceDto.id}")
          .content(objectToJson(serviceDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isBadRequest())

    when: "i update second service with non existent client id"
      serviceDto2.setClientId(new ObjectId())
      ResultActions request2 = requestAsROOT(put(ApiLayers.SERVICES + "/${serviceDto2.id}")
          .content(objectToJson(serviceDto2))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful service delete scenario"() {
    given: "there is a service in the system"
      ServiceDto serviceDto = serviceFacade.add(validServiceDto1)
    when: "i try to delete the service"
      ResultActions request = requestAsROOT(delete(ApiLayers.SERVICES + "/${serviceDto.id}"))
    then: "the request should return no content"
      request.andExpect(status().isNoContent())
    when: "i ask the system for the deleted service"
      ResultActions request2 = requestAsROOT(get(ApiLayers.SERVICES + "/${serviceDto.id}"))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "fail service delete scenario"() {
    when: "i try to delete a service that doesn't exist"
      ResultActions request = requestAsROOT(delete(ApiLayers.SERVICES + "/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request.andExpect(status().isNotFound())
  }


}