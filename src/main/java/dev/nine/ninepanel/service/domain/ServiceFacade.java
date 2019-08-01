package dev.nine.ninepanel.service.domain;

import dev.nine.ninepanel.service.domain.dto.ServiceDto;
import dev.nine.ninepanel.user.domain.UserFacade;
import dev.nine.ninepanel.user.domain.dto.UserDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ServiceFacade {

  private ServiceRepository serviceRepository;
  private ServiceCreator    serviceCreator;
  private UserFacade        userFacade;

  public ServiceFacade(ServiceRepository serviceRepository, ServiceCreator serviceCreator, UserFacade userFacade) {
    this.serviceRepository = serviceRepository;
    this.serviceCreator = serviceCreator;
    this.userFacade = userFacade;
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
    UserDto userDto = userFacade.showUserById(serviceDto.getClientId());
    return serviceRepository.save(serviceCreator.from(serviceDto)).dto();
  }
}
