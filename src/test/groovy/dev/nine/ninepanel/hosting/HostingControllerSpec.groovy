package dev.nine.ninepanel.hosting

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.hosting.domain.Hosting
import dev.nine.ninepanel.hosting.domain.HostingRepository
import dev.nine.ninepanel.hosting.domain.dto.HostingDto
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class HostingControllerSpec extends IntegrationSpec {
  @Autowired
  private HostingRepository hostingRepository

  def "successful hostings list access scenario"() {
    given: "system has three hostings"
      hostingRepository.save(Hosting.builder().clientId(authenticatedUser.id).title("1").build())
      hostingRepository.save(Hosting.builder().clientId(authenticatedUser.id).title("2").build())
      hostingRepository.save(Hosting.builder().clientId(new ObjectId()).title("3").build())
    when: "i ask system for hostings"
      ResultActions request = requestAsUser(get(ApiLayers.HOSTINGS))
    then: "i see all hostings"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(3)))
  }

  def "fail hostings list access scenario"() {
    given: "system has three hostings"
      hostingRepository.save(Hosting.builder().clientId(authenticatedUser.id).title("1").build())
      hostingRepository.save(Hosting.builder().clientId(authenticatedUser.id).title("2").build())
      hostingRepository.save(Hosting.builder().clientId(new ObjectId()).title("3").build())
    when: "i ask system for hostings as guest"
      ResultActions request = requestAsAnonymous(get(ApiLayers.HOSTINGS))
    then: "i cannot see any hosting"
      request
          .andExpect(status().isUnauthorized())
  }

  def "successful hosting access scenario"() {
    given: "system has one hosting"
      HostingDto hostingDto = hostingRepository.save(Hosting.builder().clientId(authenticatedUser.id).title("1").build()).dto()
    when: "i ask system for specific hosting"
      ResultActions request = requestAsUser(get(ApiLayers.HOSTINGS + "/${hostingDto.id.toHexString()}"))
    then: "i see all my hostings"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(hostingDto)))
  }

  def "fail hosting access as anonymous scenario"() {
    given: "system has one hosting"
      HostingDto hostingDto = hostingRepository.save(Hosting.builder().clientId(authenticatedUser.id).title("1").build()).dto()
    when: "i ask system for specific hosting"
      ResultActions request = requestAsAnonymous(get(ApiLayers.HOSTINGS + "/${hostingDto.id.toHexString()}"))
    then: "i cannot see any hosting"
      request
          .andExpect(status().isUnauthorized())
  }

  def "fail hosting access of other user scenario"() {
    given: "system has one hosting"
      HostingDto hostingDto = hostingRepository.save(Hosting.builder().clientId(new ObjectId()).title("1").build()).dto()
    when: "i ask system for specific hosting"
      ResultActions request = requestAsAnonymous(get(ApiLayers.HOSTINGS + "/${hostingDto.id.toHexString()}"))
    then: "i cannot see this hosting"
      request
          .andExpect(status().isUnauthorized())
  }
}