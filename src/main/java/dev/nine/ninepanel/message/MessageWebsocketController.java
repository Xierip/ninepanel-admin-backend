package dev.nine.ninepanel.message;

import dev.nine.ninepanel.message.domain.MessageFacade;
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto;
import dev.nine.ninepanel.message.domain.dto.MessageDto;
import javax.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebsocketController {

  private final MessageFacade         messageFacade;

  public MessageWebsocketController(MessageFacade messageFacade) {
    this.messageFacade = messageFacade;
  }

  @MessageMapping("chat/{userId}")
  @SendTo("/topic/{userId}")
  MessageDto chatMessage(@Valid MessageCreationDto messageCreationDto) {
    return messageFacade.add(messageCreationDto);
  }

}
