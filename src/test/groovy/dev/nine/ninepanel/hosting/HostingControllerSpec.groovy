package dev.nine.ninepanel.hosting

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.hosting.domain.HostingFacade
import dev.nine.ninepanel.hosting.domain.dto.HostingDto
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class HostingControllerSpec extends IntegrationSpec implements HostingData {
  @Autowired
  private HostingFacade hostingFacade

  void setup() {
    setupHostings()
  }

  def "successful hostings list access scenario"() {
    given: "system has two hostings"
      hostingFacade.add(validHostingDto1)
      hostingFacade.add(validHostingDto2)
    when: "i ask system for hostings"
      ResultActions request = requestAsUser(get(ApiLayers.HOSTINGS))
    then: "i see all hostings"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(2)))
  }

  def "fail hostings list access scenario"() {
    given: "system has two hostings"
      hostingFacade.add(validHostingDto1)
      hostingFacade.add(validHostingDto2)
    when: "i ask system for hostings as guest"
      ResultActions request = requestAsAnonymous(get(ApiLayers.HOSTINGS))
    then: "i cannot see any hosting"
      request
          .andExpect(status().isUnauthorized())
  }

  def "successful hosting access scenario"() {
    given: "system has one hosting"
      HostingDto hostingDto = hostingFacade.add(validHostingDto1)
    when: "i ask system for specific hosting"
      ResultActions request = requestAsUser(get(ApiLayers.HOSTINGS + "/${hostingDto.id}"))
    then: "i see all my hostings"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(hostingDto)))
  }

  def "fail hosting access scenario"() {
    given: "system has one hosting"
      HostingDto hostingDto = hostingFacade.add(validHostingDto1)
    when: "i ask system for specific hosting"
      ResultActions request = requestAsAnonymous(get(ApiLayers.HOSTINGS + "/${hostingDto.id}"))
    then: "i cannot see any hosting"
      request
          .andExpect(status().isUnauthorized())
    when: "i ask the system for a hosting that doest exist"
      ResultActions request2 = requestAsUser(get(ApiLayers.HOSTINGS + "/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful hosting add scenario"() {
    given: "there are no hostings in the system"
    when: "i post the hosting data to add route"
      ResultActions request = requestAsUser(post(ApiLayers.HOSTINGS)
          .content(objectToJson(validHostingDto1))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    when: "i fetch all hostings"
      ResultActions request2 = requestAsUser(get(ApiLayers.HOSTINGS))
    then: "there should be one hosting"
      request2
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(1)))
  }

  def "fail hosting add scenario"() {
    when: "i post invalid hosting data to add route"
      ResultActions request = requestAsUser(post(ApiLayers.HOSTINGS)
          .content(objectToJson(invalidHostingDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should fail"
      request.andExpect(status().isBadRequest())

    when: "i post hosting data with nonexistent client id"
      ResultActions request2 = requestAsUser(post(ApiLayers.HOSTINGS)
          .content(objectToJson(noClientHostingDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful hosting update scenario"() {
    given: "there is a hosting in the system"
      HostingDto hostingDto = hostingFacade.add(validHostingDto1)
    when: "I update the hosting with valid data"
      hostingDto.setDescription("newDescription")
      ResultActions request = requestAsUser(put(ApiLayers.HOSTINGS + "/${hostingDto.id}")
          .content(objectToJson(hostingDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isOk())
    and: "the hosting should be updated"
      request.andExpect(content().json(objectToJson(hostingDto)))
  }

  def "fail hosting update scenario"() {
    given: "there are two hostings in the system"
      HostingDto hostingDto = hostingFacade.add(validHostingDto1)
      HostingDto hostingDto2 = hostingFacade.add(validHostingDto2)

    when: "I update first hosting with invalid data"
      hostingDto.setDescription(null)
      ResultActions request = requestAsUser(put(ApiLayers.HOSTINGS + "/${hostingDto.id}")
          .content(objectToJson(hostingDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should be ok"
      request.andExpect(status().isBadRequest())

    when: "i update second hosting with non existent client id"
      hostingDto2.setClientId(new ObjectId())
      ResultActions request2 = requestAsUser(put(ApiLayers.HOSTINGS + "/${hostingDto2.id}")
          .content(objectToJson(hostingDto2))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful hosting delete scenario"() {
    given: "there is a hosting in the system"
      HostingDto hostingDto = hostingFacade.add(validHostingDto1)
    when: "i try to delete the hosting"
      ResultActions request = requestAsUser(delete(ApiLayers.HOSTINGS + "/${hostingDto.id}"))
    then: "the request should return no content"
      request.andExpect(status().isNoContent())
    when: "i ask the system for the deleted hosting"
      ResultActions request2 = requestAsUser(get(ApiLayers.HOSTINGS + "/${hostingDto.id}"))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "fail hosting delete scenario"() {
    when: "i try to delete a hosting that doesn't exist"
      ResultActions request = requestAsUser(delete(ApiLayers.HOSTINGS + "/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request.andExpect(status().isNotFound())
  }

}