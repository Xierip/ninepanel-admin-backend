package dev.nine.ninepanel.message.domain;

import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface MessageRepository extends MongoRepository<Message, ObjectId>, MessageRepositoryExtension {

  Set<Message> findAllBySenderIdOrRecipientIdOrderByCreatedDateDesc(ObjectId senderId, ObjectId recipientId);

}

