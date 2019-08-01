package dev.nine.ninepanel.service

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import dev.nine.ninepanel.service.domain.Service
import dev.nine.ninepanel.service.domain.ServiceRepository
import dev.nine.ninepanel.service.domain.dto.ServiceDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ServiceControllerSpec extends IntegrationSpec {
  @Autowired
  private ServiceRepository serviceRepository

  def "successful services list access scenario"() {
    given: "system has three services"
      serviceRepository.save(Service.builder().clientId(authenticatedUser.id).title("1").build())
      serviceRepository.save(Service.builder().clientId(authenticatedUser.id).title("2").build())
      serviceRepository.save(Service.builder().clientId(new ObjectId()).title("3").build())

    when: "i ask system for services"
      ResultActions request = requestAsUser(get(ApiLayers.SERVICES))

    then: "i see all my services"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(2)))
  }

  def "fail services list access scenario"() {
    given: "system has three services"
      serviceRepository.save(Service.builder().clientId(authenticatedUser.id).title("1").build())
      serviceRepository.save(Service.builder().clientId(authenticatedUser.id).title("2").build())
      serviceRepository.save(Service.builder().clientId(new ObjectId()).title("3").build())

    when: "i ask system for services as guest"
      ResultActions request = requestAsAnonymous(get(ApiLayers.SERVICES))

    then: "i cannot see any service"
      request
          .andExpect(status().isUnauthorized())
  }

  def "successful service access scenario"() {
    given: "system has one service"
      ServiceDto serviceDto = serviceRepository.save(Service.builder().clientId(authenticatedUser.id).title("1").build()).dto()

    when: "i ask system for specific service"
      ResultActions request = requestAsUser(get(ApiLayers.SERVICES + "/${serviceDto.id.toHexString()}"))

    then: "i see all my services"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(serviceDto)))
  }

  def "fail service access as anonymous scenario"() {
    given: "system has one service"
      ServiceDto serviceDto = serviceRepository.save(Service.builder().clientId(authenticatedUser.id).title("1").build()).dto()

    when: "i ask system for specific service"
      ResultActions request = requestAsAnonymous(get(ApiLayers.SERVICES + "/${serviceDto.id.toHexString()}"))

    then: "i cannot see any service"
      request
          .andExpect(status().isUnauthorized())
  }

  def "fail service access of other user scenario"() {
    given: "system has one service"
      ServiceDto serviceDto = serviceRepository.save(Service.builder().clientId(new ObjectId()).title("1").build()).dto()

    when: "i ask system for specific service"
      ResultActions request = requestAsAnonymous(get(ApiLayers.SERVICES + "/${serviceDto.id.toHexString()}"))

    then: "i cannot see this service"
      request
          .andExpect(status().isUnauthorized())
  }
}