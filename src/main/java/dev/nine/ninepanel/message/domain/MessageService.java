package dev.nine.ninepanel.message.domain;

import com.google.common.collect.Sets;
import dev.nine.ninepanel.clients.domain.ClientsFacade;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;

class MessageService {

  private final ClientsFacade     clientsFacade;
  private final MessageRepository messageRepository;

  MessageService(ClientsFacade clientsFacade, MessageRepository messageRepository) {
    this.clientsFacade = clientsFacade;
    this.messageRepository = messageRepository;
  }

  public Set<Map<String, Object>> showAll(ObjectId userId) {
    Set<Map<String, Object>> jsonResponse = new HashSet<>();

    if (userId != null) {
      Set<Message> messages = this.messageRepository.findAllBySenderIdOrRecipientIdOrderByCreatedDateDesc(userId, userId);
      this.addConversation(jsonResponse, userId, messages);
      return jsonResponse;
    }

    Map<ObjectId, Set<Message>> messages = new HashMap<>();

    this.messageRepository.findAll().forEach(message -> {
      ObjectId clientId = message.getRecipientId() == null ? message.getSenderId() : message.getRecipientId();
      messages.compute(clientId, (key, value) -> this.computeMessagesCallback(value, message));
    });

    messages.forEach((key, value) -> this.addConversation(jsonResponse, key, value));
    return jsonResponse;
  }

  private Set<Message> computeMessagesCallback(Set<Message> value, Message message) {
    if (value == null) {
      return Sets.newHashSet(message);
    } else {
      value.add(message);
      return value;
    }
  }

  private void addConversation(Set<Map<String, Object>> jsonResponse, ObjectId clientId, Set<Message> messages) {
    jsonResponse.add(Map.of(
        "clientId", clientId.toHexString(),
        "clientDisplayName", this.clientsFacade.showById(clientId).getDisplayName(),
        "messages", messages)
    );
  }

}
