package dev.nine.ninepanel.notification.domain;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface NotificationRepository extends MongoRepository<Notification, ObjectId>, NotificationRepositoryExtension {

  Page<Notification> findAllByClientId(ObjectId clientId, Pageable pageable);
}
