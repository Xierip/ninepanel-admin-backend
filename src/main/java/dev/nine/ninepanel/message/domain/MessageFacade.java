package dev.nine.ninepanel.message.domain;

import com.pusher.rest.Pusher;
import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.infrastructure.constant.PusherNames;
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto;
import dev.nine.ninepanel.message.domain.dto.MessageDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class MessageFacade {

  private final MessageRepository messageRepository;
  private final MessageCreator    messageCreator;
  private final Pusher            pusher;
  private final ClientsFacade     clientsFacade;

  MessageFacade(MessageRepository messageRepository, MessageCreator messageCreator, Pusher pusher,
      ClientsFacade clientsFacade) {
    this.messageRepository = messageRepository;
    this.messageCreator = messageCreator;
    this.pusher = pusher;
    this.clientsFacade = clientsFacade;
  }


  public Page<MessageDto> showAll(Pageable pageable, ObjectId userId) {
    return messageRepository.findAllBySenderIdOrRecipientIdOrderByCreatedDateDesc(pageable, userId, userId).map(Message::dto);
  }

  public MessageDto add(MessageCreationDto messageCreationDto) {
    clientsFacade.checkIfExists(messageCreationDto.getRecipientId());

    MessageDto messageDto = messageRepository.save(messageCreator.from(messageCreationDto)).dto();

    pusher.trigger(PusherNames.CHAT_CHANNEL_PREFIX + messageDto.getRecipientId(), PusherNames.NEW_MESSAGE_EVENT, messageDto.getBody());

    return messageDto;
  }

  public void readNewFrom(ObjectId userId) {
    messageRepository.readAllUnread(userId);
  }
}
