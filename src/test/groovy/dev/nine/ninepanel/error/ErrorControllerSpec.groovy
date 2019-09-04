package dev.nine.ninepanel.error

import dev.nine.ninepanel.base.IntegrationSpec
import org.springframework.test.web.servlet.ResultActions

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

class ErrorControllerSpec extends IntegrationSpec {

  def "error endpoint test"() {
    when: "I go to /error"
      ResultActions errorEndpoint = requestAsRoot(get("/error"))
    then:
      errorEndpoint.andExpect(content().json("""
        {
          "code": 200,
          "message": "None"
        }  
        """))
  }

}
