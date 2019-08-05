package dev.nine.ninepanel.service.type

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.service.type.domain.ServiceTypeFacade
import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ServiceTypeControllerSpec extends IntegrationSpec implements ServiceTypeData {

  @Autowired
  ServiceTypeFacade serviceTypeFacade

  def "successful fetch service types scenario"() {
    given: "there is a service type in the system"
      serviceTypeFacade.add(validServiceTypeDto)
    when: "i access the service types endpoint"
      ResultActions request = requestAsUser(get("/api/service-types"))
    then: "the request should be okay"
      request.andExpect(status().isOk())
    and: "i should get a list with 1 service type"
      request.andExpect(jsonPath("\$", hasSize(1)))
  }

  def "successful add service type scenario"() {
    given: "i have valid service type creation data"
    when: "i post it to add route"
      ResultActions request = requestAsUser(post("/api/service-types")
          .content(objectToJson(validServiceTypeDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    when: "i fetch the service types"
      ResultActions request2 = requestAsUser(get("/api/service-types"))
    then: "there should be one service type in the system"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(1)))
  }

  def "fail add service type scenario"() {
    given: "i have invalid service type creation data"
    when: "i post it to add route"
      ResultActions request = requestAsUser(post("/api/service-types")
          .content(objectToJson(invalidServiceTypeDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())
  }

  def "successful update service type scenario"() {
    given: "there is a service type in the system"
      ServiceTypeDto serviceTypeDto = serviceTypeFacade.add(validServiceTypeDto)
    when: "i update the service type with valid data"
      serviceTypeDto.name = "NewName"
      ResultActions request = requestAsUser(put("/api/service-types/${serviceTypeDto.id}")
          .content(objectToJson(serviceTypeDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the service type should be updated"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(serviceTypeDto)))
  }

  def "fail update type scenario"() {
    given: "there is a service type in the system"
      ServiceTypeDto serviceTypeDto = serviceTypeFacade.add(validServiceTypeDto)
    when: "i update the service type with invalid data"
      ResultActions request = requestAsUser(put("/api/service-types/${serviceTypeDto.id}")
          .content(objectToJson(invalidServiceTypeDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())

    when: "i try to update a service type that doesn't exist"
      ResultActions request2 = requestAsUser(put("/api/service-types/${new ObjectId()}")
      .content(objectToJson(validServiceTypeDto))
      .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful delete service type scenario"() {
    given: "there is a service type in the system"
      ServiceTypeDto serviceTypeDto = serviceTypeFacade.add(validServiceTypeDto)
    when: "i delete the service type"
      ResultActions request = requestAsUser(delete("/api/service-types/${serviceTypeDto.id}"))
    then: "the request should return no content"
      request.andExpect(status().isNoContent())

    when: "i fetch the service types"
      ResultActions request2 = requestAsUser(get("/api/service-types"))
    then: "there should be zero service types in the system"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(0)))

  }

  def "fail delete service type scenario"() {
    when: "i try to delete a service type that doesn't exist"
      ResultActions request = requestAsUser(delete("/api/service-types/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request.andExpect(status().isNotFound())
  }

}