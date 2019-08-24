package dev.nine.ninepanel.message;

import dev.nine.ninepanel.message.domain.MessageFacade;
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto;
import dev.nine.ninepanel.websockets.domain.StompPrincipal;
import java.security.Principal;
import org.bson.types.ObjectId;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
class MessageWebsocketController {

  private final MessageFacade         messageFacade;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public MessageWebsocketController(MessageFacade messageFacade, SimpMessagingTemplate simpMessagingTemplate) {
    this.messageFacade = messageFacade;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @MessageMapping("/chat.{userId}")
  void chatMessage(Principal principal, MessageCreationDto messageCreationDto, @DestinationVariable ObjectId userId) {
    StompPrincipal user = (StompPrincipal) principal;
    if (user.getAdmin()) {
      simpMessagingTemplate.convertAndSend("/topic/chat." + userId,
          messageFacade.addAdminMessage(messageCreationDto.getBody(), userId));
    } else {
      simpMessagingTemplate.convertAndSend("/topic/chat." + userId,
          messageFacade.addClientMessage(messageCreationDto.getBody(), userId));
    }

  }

}
