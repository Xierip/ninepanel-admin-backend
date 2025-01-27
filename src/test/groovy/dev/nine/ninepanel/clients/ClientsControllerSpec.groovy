package dev.nine.ninepanel.clients

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.clients.domain.dto.ClientDto
import dev.nine.ninepanel.infrastructure.constant.ApiLayers
import dev.nine.ninepanel.token.domain.TokenFacade
import dev.nine.ninepanel.token.domain.TokenType
import dev.nine.ninepanel.token.domain.dto.TokenDto
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions

import java.time.LocalDateTime

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class ClientsControllerSpec extends IntegrationSpec implements ClientsData {

  @Autowired
  private TokenFacade tokenFacade

  def "successful fetch clients list scenario"() {
    given: "there are 2 clients in the system"
      setUpClient("client1@test.com")
      setUpClient("client2@test.com")
    when: "i ask the system for a list of clients"
      ResultActions request = requestAsRoot(get(ApiLayers.CLIENTS))
      request.andExpect(status().isOk())
    then: "i should get a list of 2 clients"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$.content", hasSize(2)))
  }

  def "fail fetch clients list scenario"() {
    when: "i ask the system for a list of clients when im not logged in"
      ResultActions request = requestAsAnonymous(get(ApiLayers.CLIENTS))
    then: "the request should be unauthorized"
      request.andExpect(status().isUnauthorized())
  }

  def "successful fetch client scenario"() {
    given: "there is a client in the system"
      ClientDto clientDto = setUpClient("test@test.com")
    when: "i ask for the client"
      ResultActions request = requestAsRoot(get(ApiLayers.CLIENTS + "/${clientDto.id}"))
    then: "i should get the specified client"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(clientDto)))
  }

  def "successful fetch client by staff code scenario"() {
    given: "there is a client and staff code in the system"
      ClientDto clientDto = setUpClient("test@test.com")
      TokenDto tokenDto = tokenFacade.addToken(TokenDto.builder().userId(clientDto.getId()).expirationDate(LocalDateTime.now().plusMinutes(2))
          .tokenType(TokenType.STAFF_TOKEN).build())
    when: "i ask for the client"
      ResultActions request = requestAsRoot(get(ApiLayers.CLIENTS + "/?code=${tokenDto.body}"))
    then: "i should get the specified client"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(clientDto)))
  }

  def "fail fetch client by staff code scenario"() {
    given: "there is code for not exists user in the system"
      TokenDto tokenDto = tokenFacade.addToken(TokenDto.builder().userId(new ObjectId()).expirationDate(LocalDateTime.now().plusMinutes(2))
          .tokenType(TokenType.STAFF_TOKEN).build())
    when: "i ask for the client"
      ResultActions request = requestAsRoot(get(ApiLayers.CLIENTS + "/?code=${tokenDto.body}"))
    then: "the request should throw 404"
      request
          .andExpect(status().isNotFound())
    when: "i ask for the client by bad code"
      request = requestAsRoot(get(ApiLayers.CLIENTS + "/?code=666666"))
    then: "the request should throw 404"
      request
          .andExpect(status().isNotFound())
  }


  def "fail fetch client scenario"() {
    when: "i ask for a client that doesn't exist"
      ResultActions request = requestAsRoot(get(ApiLayers.USERS + "/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request.andExpect(status().isNotFound())
  }

  def "successful update client scenario"() {
    given: "there is a client in the system"
      ClientDto clientDto = setUpClient("test@test.com")
    when: "i update the client with valid data"
      clientDto.phoneNumber = "121 123 827"
      ResultActions request = requestAsRoot(put(ApiLayers.CLIENTS + "/${clientDto.id}")
          .content(objectToJson(clientDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the client should be updated"
      request
          .andExpect(status().isOk())
          .andExpect(content().json(objectToJson(clientDto)))
  }

  def "fail update client scenario"() {
    given: "there is a client in the system"
      ClientDto clientDto = setUpClient("test@test.com")
    when: "i try to update a client that doesn't exist"
      ResultActions request2 = requestAsRoot(put(ApiLayers.CLIENTS + "/${new ObjectId()}")
          .content(objectToJson(clientDto))
          .contentType(MediaType.APPLICATION_JSON_UTF8))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "successful delete client scenario"() {
    given: "there is a client in the system"
      ClientDto clientDto = setUpClient("test@lmao.com")
    when: "i try to delete the client"
      ResultActions request = requestAsRoot(delete(ApiLayers.CLIENTS + "/${clientDto.id}"))
    then: "the request should return no content"
      request.andExpect(status().isNoContent())
    when: "i ask for the deleted client"
      ResultActions request2 = requestAsRoot(get(ApiLayers.CLIENTS + "/${clientDto.id}"))
    then: "the request should return 404 not found"
      request2.andExpect(status().isNotFound())
  }

  def "fail delete client scenario"() {
    when: "i try to delete a client that doesn't exist"
      ResultActions request = requestAsRoot(delete(ApiLayers.CLIENTS + "/${new ObjectId()}"))
    then: "the request should return 404 not found"
      request.andExpect(status().isNotFound())
  }

  def "successful fetch clients list with search term scenario"() {
    given: "there are 2 clients in the system"
      setUpClient("someguy@test.com")
      setUpClient("otherguy@test.com")
    when: "i ask the system for a list of clients matching my search term"
      ResultActions request = requestAsRoot(get(ApiLayers.CLIENTS)
          .param("search", "other"))
      request.andExpect(status().isOk())
    then: "i should get a list of 1 clients"
      request
          .andExpect(status().isOk())
          .andExpect(jsonPath("\$", hasSize(1)))
  }

}
