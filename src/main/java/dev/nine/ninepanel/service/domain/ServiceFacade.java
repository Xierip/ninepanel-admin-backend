package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.service.domain.dto.ServiceDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ServiceFacade {

  private ServiceRepository serviceRepository;
  private ServiceCreator    serviceCreator;
  private ClientsFacade     clientsFacade;

  public ServiceFacade(ServiceRepository serviceRepository, ServiceCreator serviceCreator, ClientsFacade clientsFacade) {
    this.serviceRepository = serviceRepository;
    this.serviceCreator = serviceCreator;
    this.clientsFacade = clientsFacade;
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

  public ServiceDto add(ServiceDto serviceDto) {
    clientsFacade.checkIfExists(serviceDto.getClientId());
    return serviceRepository.save(serviceCreator.from(serviceDto)).dto();
  }

  public ServiceDto update(ObjectId serviceId, ServiceDto serviceDto) {
    serviceDto.setId(serviceId);
    clientsFacade.checkIfExists(serviceDto.getClientId());
    return serviceRepository.updateOrThrow(serviceCreator.from(serviceDto, serviceRepository.findByIdOrThrow(serviceDto.getId()))).dto();
  }

  public void delete(ObjectId serviceId) {
    serviceRepository.deleteByIdOrThrow(serviceId);
  }
}
