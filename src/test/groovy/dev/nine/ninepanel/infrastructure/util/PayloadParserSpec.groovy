package dev.nine.ninepanel.infrastructure.util

import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification


class PayloadParserSpec extends Specification {

  def "success parse single field"() {
    given: "i have payload sent by client"
      Map<String, Object> payload = Map.of("password", "test")
    when: "i try to parse password field"
      String password = PayloadParser.parseSingleField(payload, "password", String.class)
    then: "i get this field"
      password == "test"
  }

  def "fail parse not exists field"() {
    given: "i have empty payload sent by client"
      Map<String, Object> payload = Map.of()
    when: "i try to parse password field"
      String password = PayloadParser.parseSingleField(payload, "password", String.class)
    then: "i get exception"
      thrown(ResponseStatusException)
  }

  def "fail parse bad type field"() {
    given: "i have empty payload sent by client"
      Map<String, Object> payload = Map.of("password", 123)
    when: "i try to parse password field"
      String password = PayloadParser.parseSingleField(payload, "password", String.class)
    then: "i get exception"
      thrown(ResponseStatusException)
  }
}