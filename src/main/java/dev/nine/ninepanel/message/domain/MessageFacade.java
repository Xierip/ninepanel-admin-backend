package dev.nine.ninepanel.message.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.message.domain.dto.MessageDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class MessageFacade {

  private final MessageRepository messageRepository;
  private final MessageCreator    messageCreator;
  private final ClientsFacade     clientsFacade;

  MessageFacade(MessageRepository messageRepository, MessageCreator messageCreator,
      ClientsFacade clientsFacade) {
    this.messageRepository = messageRepository;
    this.messageCreator = messageCreator;
    this.clientsFacade = clientsFacade;
  }


  public Page<MessageDto> showAll(Pageable pageable, ObjectId userId) {
    return messageRepository.findAllBySenderIdOrRecipientIdOrderByCreatedDateDesc(pageable, userId, userId).map(Message::dto);
  }

  public MessageDto add(String messageBody, ObjectId recipientId) {
    clientsFacade.checkIfExists(recipientId);
    return messageRepository.save(messageCreator.from(messageBody, recipientId)).dto();
  }

  public void readNewFrom(ObjectId userId) {
    messageRepository.readAllUnread(userId);
  }
}
