package dev.nine.ninepanel.message.domain;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface MessageRepository extends MongoRepository<Message, ObjectId>, MessageRepositoryExtension {

  Page<Message> findAllBySenderIdOrRecipientIdOrderByCreatedDateDesc(Pageable pageable, ObjectId senderId, ObjectId recipientId);

}

