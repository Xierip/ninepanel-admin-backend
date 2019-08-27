package dev.nine.ninepanel.message.domain;

import com.google.common.collect.Sets;
import dev.nine.ninepanel.clients.domain.ClientsFacade;
import java.util.Map;
import java.util.Set;
import org.bson.types.ObjectId;

class MessageService {

  private final ClientsFacade clientsFacade;

  MessageService(ClientsFacade clientsFacade) {
    this.clientsFacade = clientsFacade;
  }

  Set<Message> computeMessagesCallback(Set<Message> value, Message message) {
    if (value == null) {
      return Sets.newHashSet(message);
    } else {
      value.add(message);
      return value;
    }
  }

  void addConversation(Set<Map<String, Object>> jsonResponse, ObjectId clientId, Set<Message> messages) {
    jsonResponse.add(Map.of(
        "clientId", clientId.toHexString(),
        "clientDisplayName", this.clientsFacade.showById(clientId).getDisplayName(),
        "messages", messages)
    );
  }

}
