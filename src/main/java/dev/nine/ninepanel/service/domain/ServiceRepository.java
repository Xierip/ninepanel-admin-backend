package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.service.domain.exceptions.ServiceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.Repository;

interface ServiceRepository extends Repository<Service, ObjectId> {

  default Service findByIdOrThrow(ObjectId id) {
    return this.findById(id).orElseThrow(ServiceNotFoundException::new);
  }

  default Service findByIdAndClientIdOrThrow(ObjectId id, ObjectId clientId) {
    return this.findByIdAndClientId(id, clientId).orElseThrow(ServiceNotFoundException::new);
  }

  Optional<Service> findByIdAndClientId(ObjectId id, ObjectId clientId);

  Optional<Service> findById(ObjectId id);

  List<Service> findAllByClientId(ObjectId clientId);

  Service save(Service service);
}
