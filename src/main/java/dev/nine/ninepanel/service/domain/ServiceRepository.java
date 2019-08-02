package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.service.domain.exceptions.ServiceNotFoundException;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ServiceRepository extends MongoRepository<Service, ObjectId> {

  default Service findByIdOrThrow(ObjectId id) {
    return this.findById(id).orElseThrow(ServiceNotFoundException::new);
  }

  default Service findByIdAndClientIdOrThrow(ObjectId id, ObjectId clientId) {
    return this.findByIdAndClientId(id, clientId).orElseThrow(ServiceNotFoundException::new);
  }

  Optional<Service> findByIdAndClientId(ObjectId id, ObjectId clientId);

  Optional<Service> findById(ObjectId id);

  Page<Service> findAllByClientId(ObjectId clientId, Pageable pageable);

  default void deleteByIdOrThrow(ObjectId serviceId) {
    checkIfExistsOrThrow(serviceId);
    deleteById(serviceId);
  }

  default Service updateOrThrow(Service service) {
    checkIfExistsOrThrow(service.getId());
    return save(service);
  }

  private void checkIfExistsOrThrow(ObjectId serviceId) {
    if (!this.existsById(serviceId)) {
      throw new ServiceNotFoundException(serviceId);
    }
  }
}
