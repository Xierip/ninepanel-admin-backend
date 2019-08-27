package dev.nine.ninepanel.message.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.message.domain.dto.MessageDto;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;

public class MessageFacade {

  private final MessageRepository messageRepository;
  private final MessageService messageService;
  private final MessageCreator messageCreator;
  private final ClientsFacade clientsFacade;

  MessageFacade(MessageRepository messageRepository, MessageService messageService,
      MessageCreator messageCreator, ClientsFacade clientsFacade) {
    this.messageRepository = messageRepository;
    this.messageService = messageService;
    this.messageCreator = messageCreator;
    this.clientsFacade = clientsFacade;
  }


  public Set<Map<String, Object>> showAll(ObjectId userId) {
    return this.messageService.showAll(userId);
  }

  public MessageDto addAdminMessage(String messageBody, ObjectId recipientId) {
    this.clientsFacade.checkIfExists(recipientId);
    return this.messageRepository.save(this.messageCreator.from(messageBody, recipientId, null))
        .dto();
  }

  public MessageDto addClientMessage(String messageBody, ObjectId senderId) {
    this.clientsFacade.checkIfExists(senderId);
    return this.messageRepository.save(this.messageCreator.from(messageBody, null, senderId)).dto();
  }

  public void readNewFrom(ObjectId userId) {
    this.messageRepository.readAllUnread(userId);
  }
}
