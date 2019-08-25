package dev.nine.ninepanel.message.domain;

import org.bson.types.ObjectId;

class MessageCreator {

  Message from(String messageBody, ObjectId recipientId, ObjectId senderId) {
    return Message.builder()
        .body(messageBody)
        .recipientId(recipientId)
        .senderId(senderId)
        .read(false)
        .build();
  }

}
