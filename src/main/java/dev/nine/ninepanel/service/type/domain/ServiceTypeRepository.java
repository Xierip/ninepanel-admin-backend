package dev.nine.ninepanel.service.type.domain;

import dev.nine.ninepanel.service.type.domain.exception.ServiceTypeNotFoundException;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

interface ServiceTypeRepository extends CrudRepository<ServiceType, ObjectId> {

  List<ServiceType> findAll();

  default void deleteByIdOrThrow(ObjectId id) {
    if (!existsById(id)) {
      throw new ServiceTypeNotFoundException(id);
    }
    deleteById(id);
  }

  default ServiceType updateOrThrow(ServiceType serviceType) {
    if (!existsById(serviceType.getId())) {
      throw new ServiceTypeNotFoundException(serviceType.getId());
    }
    return save(serviceType);
  }

  default ServiceType findByIdOrThrow(ObjectId id) {
    return findById(id).orElseThrow(() -> new ServiceTypeNotFoundException(id));
  }
}
