package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.service.domain.dto.ServiceDto;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

public class ServiceFacade {

  private ServiceRepository serviceRepository;

  public ServiceFacade(ServiceRepository serviceRepository) {
    this.serviceRepository = serviceRepository;
  }

  public List<ServiceDto> showAllForClient(ObjectId clientId) {
    return serviceRepository.findAllByClientId(clientId).stream().map(Service::dto).collect(Collectors.toList());
  }

  public ServiceDto showForClient(ObjectId serviceId, ObjectId clientId) {
    return serviceRepository.findByIdAndClientIdOrThrow(serviceId, clientId).dto();
  }
}
