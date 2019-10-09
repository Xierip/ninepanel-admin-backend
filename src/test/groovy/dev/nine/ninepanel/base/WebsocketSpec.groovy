package dev.nine.ninepanel.base

import dev.nine.ninepanel.clients.ClientsData
import dev.nine.ninepanel.clients.domain.dto.ClientDto
import dev.nine.ninepanel.token.domain.TokenFacade
import dev.nine.ninepanel.token.domain.TokenType
import dev.nine.ninepanel.token.domain.dto.TokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.WebSocketTransport

import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebsocketSpec extends IntegrationSpec implements ClientsData {

  @Autowired
  TokenFacade tokenFacade

  @Value("\${local.server.port}")
  String port
  String WEBSOCKET_URI

  WebSocketStompClient stompClient
  ClientDto websocketUserClient

  TokenDto clientWebsocketToken
  TokenDto adminWebsocketToken

  void setup() {
    websocketUserClient = setUpClient("some@client.com")

    WEBSOCKET_URI = "http://localhost:${port}/ws"

    SockJsClient sockJsClient = new SockJsClient(Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient())))
    stompClient = new WebSocketStompClient(sockJsClient)

    clientWebsocketToken = tokenFacade.addToken(TokenDto.builder()
        .userId(websocketUserClient.id)
        .tokenType(TokenType.WEBSOCKET_TOKEN)
        .build())


    stompClient.setMessageConverter(new MappingJackson2MessageConverter())
  }

  StompSession connectAsClient() {
    WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders()
    webSocketHttpHeaders.set("cookie", "websocketToken=${clientWebsocketToken.body}")

    return stompClient
        .connect(WEBSOCKET_URI, webSocketHttpHeaders, new StompSessionHandlerAdapter() {})
        .get(1, TimeUnit.SECONDS)
  }

  StompSession connectAsAdmin() {
    WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders()
    webSocketHttpHeaders.set("cookie", "websocketToken=${adminWebsocketToken.body}")

    return stompClient
        .connect(WEBSOCKET_URI, webSocketHttpHeaders, new StompSessionHandlerAdapter() {})
        .get(1, TimeUnit.SECONDS)
  }

}
