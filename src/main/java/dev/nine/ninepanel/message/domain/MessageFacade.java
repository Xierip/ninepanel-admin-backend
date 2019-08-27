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
    Set<Map<String, Object>> jsonResponse = new HashSet<>();

    if (userId != null) {
      Set<Message> messages = this.messageRepository.findAllBySenderIdOrRecipientIdOrderByCreatedDateDesc(userId, userId);
      messageService.addConversation(jsonResponse, userId, messages);
      return jsonResponse;
    }

    Map<ObjectId, Set<Message>> messages = new HashMap<>();

    this.messageRepository.findAll().forEach(message -> {
      ObjectId clientId = message.getRecipientId() == null ? message.getSenderId() : message.getRecipientId();
      messages.compute(clientId, (key, value) -> messageService.computeMessagesCallback(value, message));
    });

    messages.forEach((key, value) -> messageService.addConversation(jsonResponse, key, value));
    return jsonResponse;
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
