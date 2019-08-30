package dev.nine.ninepanel.message

import dev.nine.ninepanel.base.WebsocketSpec
import org.bson.types.ObjectId
import org.springframework.lang.Nullable
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession

import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class MessageWebsocketControllerSpec extends WebsocketSpec implements MessageData {

  CompletableFuture<Object> completableFuture

  void setup() {
    completableFuture = new CompletableFuture<>()
  }

  def "chat endpoint admin successful scenario"() {
    when: "i try to connect to websocket server"
      StompSession stompSession = connectAsAdmin()

    then: "i should be connected"
      stompSession.isConnected()
      println "I am connected ${stompSession}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"

    when: "i subscribe to some user's chat endpoint"
      stompSession.subscribe("/topic/chat.${websocketUserClient.id}", new MessageStompFrameHandler())

    and: "i send a message to that endpoint"
      stompSession.send("/app/chat.${websocketUserClient.id}", validMessageCreationDto)

    then: "i should receive this message from my subscription"
      completableFuture.get(1, TimeUnit.SECONDS) != null
  }

  def "chat endpoint client successful scenario"() {
    when: "i try to connect to websocket server"
      StompSession stompSession = connectAsClient()

    then: "i should be connected"
      stompSession.isConnected()
      println "I am connected ${stompSession}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"
      println "I am connected ${stompSession.isConnected()}"

    when: "i subscribe to my chat endpoint"
      stompSession.subscribe("/topic/chat.${websocketUserClient.id}", new MessageStompFrameHandler())

    and: "i send a message to that endpoint"
      stompSession.send("/app/chat.${websocketUserClient.id}", validMessageCreationDto)

    then: "i should receive this message from my subscription"
      completableFuture.get(1, TimeUnit.SECONDS) != null
  }

  def "chat endpoint client fail scenario"() {
    ObjectId otherClientId = new ObjectId()

    when: "i try to connect to websocket server"
      StompSession stompSession = connectAsClient()

    then: "i should be connected"
      stompSession.isConnected()

    when: "i subscribe to some other user's chat endpoint"
      stompSession.subscribe("/topic/chat.${otherClientId}", new MessageStompFrameHandler())

    and: "i send a message to that endpoint and check if it arrives to my subscription"
      stompSession.send("/app/chat.${otherClientId}", validMessageCreationDto)
      completableFuture.get(1, TimeUnit.SECONDS)

    then: "it shouldn't arrive and handler exception should be thrown"
      thrown(TimeoutException)
  }

  private class MessageStompFrameHandler implements StompFrameHandler {

    @Override
    Type getPayloadType(StompHeaders headers) {
      return Object.class
    }

    @Override
    void handleFrame(StompHeaders headers, @Nullable Object payload) {
      completableFuture.complete(payload)
    }
  }

}
