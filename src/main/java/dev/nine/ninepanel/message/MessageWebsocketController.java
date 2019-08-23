package dev.nine.ninepanel.message;

import dev.nine.ninepanel.message.domain.MessageFacade;
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto;
import dev.nine.ninepanel.message.domain.dto.MessageDto;
import org.bson.types.ObjectId;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebsocketController {

  private final MessageFacade         messageFacade;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public MessageWebsocketController(MessageFacade messageFacade, SimpMessagingTemplate simpMessagingTemplate) {
    this.messageFacade = messageFacade;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @MessageMapping("/chat.{userId}")
  void chatMessage(MessageCreationDto messageCreationDto, @DestinationVariable ObjectId userId) throws Exception {
    MessageDto messageDto = messageFacade.add(messageCreationDto.getBody(), userId);
    simpMessagingTemplate.convertAndSend("/topic/chat." + userId, messageDto);
  }

}
