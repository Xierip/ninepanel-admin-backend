package dev.nine.ninepanel.notification.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface NotificationRepository extends MongoRepository<Notification, ObjectId>, NotificationRepositoryExtension {

}
