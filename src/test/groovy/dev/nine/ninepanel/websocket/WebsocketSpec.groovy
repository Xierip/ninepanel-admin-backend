package dev.nine.ninepanel.websocket

import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.clients.ClientsData
import dev.nine.ninepanel.clients.domain.dto.ClientDto
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto
import dev.nine.ninepanel.token.domain.TokenFacade
import dev.nine.ninepanel.token.domain.TokenType
import dev.nine.ninepanel.token.domain.dto.TokenDto
import dev.nine.ninepanel.websockets.websockettoken.WebSocketTokenFacade
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.WebSocketTransport

import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebsocketSpec extends IntegrationSpec implements ClientsData {

  @Autowired
  WebSocketTokenFacade webSocketTokenFacade;
  @Autowired
  TokenFacade tokenFacade;

  @Value("\${local.server.port}")
  String port
  String WEBSOCKET_URI
  CompletableFuture<Object> completableFuture
  WebSocketStompClient stompClient
  WebSocketHttpHeaders webSocketHttpHeaders
  StompHeaders connectHeaders
  ClientDto clientDto

  void setup() {
    clientDto = setUpClient("some@client.com")
    completableFuture = new CompletableFuture<>()
    WEBSOCKET_URI = "ws://localhost:${port}/ws"
    stompClient = new WebSocketStompClient(
        new SockJsClient(
            Collections.singletonList(
                new WebSocketTransport(new StandardWebSocketClient())
            )
        )
    )

    /* client token */
    TokenDto tokenDto = tokenFacade.addToken(TokenDto.builder().userId(clientDto.id).tokenType(TokenType.WEBSOCKET_TOKEN).build())

    /* admin token */
    TokenDto token = webSocketTokenFacade.getOrAddToken(authenticatedUser.id)

    stompClient.setMessageConverter(new MappingJackson2MessageConverter())
    webSocketHttpHeaders = new WebSocketHttpHeaders()
    webSocketHttpHeaders.set("Authorization", "PIZDA ${tokenDto.body}")
  }

  def "test websocket endpoint"() {
    when: "i try to connect to websocket server"
      StompSession stompSession = stompClient
          .connect(WEBSOCKET_URI, webSocketHttpHeaders, new StompSessionHandlerAdapter() {})
          .get(1, TimeUnit.SECONDS)

    then: "i should be connected"
      stompSession.isConnected()

    when: "i subscribe to an endpoint"
      stompSession.subscribe("/topic/chat.${clientDto.id.toHexString()}", new MessageStompFrameHandler())

    and: "i send a message to that endpoint"
      MessageCreationDto messageCreationDto = MessageCreationDto.builder().body("test").build()
      stompSession.send("/app/chat.${clientDto.id.toHexString()}", messageCreationDto)

    then: "i should receive this message from my subscription"
      Assert.assertNotNull(completableFuture.get(1, TimeUnit.SECONDS))
  }


  private class MessageStompFrameHandler implements StompFrameHandler {
    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
      return Object.class
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
      completableFuture.complete(o)
    }
  }

}
