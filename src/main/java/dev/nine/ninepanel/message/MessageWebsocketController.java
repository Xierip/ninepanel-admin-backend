package dev.nine.ninepanel.message;

import dev.nine.ninepanel.message.domain.MessageFacade;
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto;
import dev.nine.ninepanel.message.domain.dto.MessageDto;
import java.time.LocalDateTime;
import javax.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebsocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final MessageFacade         messageFacade;

  public MessageWebsocketController(SimpMessagingTemplate simpMessagingTemplate, MessageFacade messageFacade) {
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.messageFacade = messageFacade;
  }

  @MessageMapping("chat.message")
  void chatMessage(@Valid MessageCreationDto messageCreationDto) {
    MessageDto messageDto = createMessageDto(messageCreationDto);
    messageFacade.add(messageCreationDto);

    simpMessagingTemplate.convertAndSendToUser(messageCreationDto.getRecipientId().toHexString(), "/queue/private", messageDto);
  }

  private MessageDto createMessageDto(MessageCreationDto messageCreationDto) {
    return MessageDto.builder()
        .read(false)
        .recipientId(messageCreationDto.getRecipientId())
        .body(messageCreationDto.getBody())
        .createdDate(LocalDateTime.now())
        .build();
  }
}
