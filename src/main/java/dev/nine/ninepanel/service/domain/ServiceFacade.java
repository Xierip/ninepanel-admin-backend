package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.service.domain.dto.ServiceDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ServiceFacade {

  private ServiceRepository serviceRepository;

  public ServiceFacade(ServiceRepository serviceRepository) {
    this.serviceRepository = serviceRepository;
  }


  public ServiceDto showForClient(ObjectId serviceId, ObjectId clientId) {
    return serviceRepository.findByIdAndClientIdOrThrow(serviceId, clientId).dto();
  }

  public Page<ServiceDto> showAll(Pageable pageable, ObjectId userId) {
    if (userId != null) {
      return serviceRepository.findAllByClientId(userId, pageable).map(Service::dto);
    }
    return serviceRepository.findAll(pageable).map(Service::dto);
  }

  public ServiceDto show(ObjectId serviceId) {
    return serviceRepository.findByIdOrThrow(serviceId).dto();
  }
}
