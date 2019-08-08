package dev.nine.ninepanel.service.type.domain;

import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

public class ServiceTypeFacade {

  private final ServiceTypeRepository serviceTypeRepository;
  private final ServiceTypeCreator    serviceTypeCreator;

  public ServiceTypeFacade(ServiceTypeRepository serviceTypeRepository, ServiceTypeCreator serviceTypeCreator) {
    this.serviceTypeRepository = serviceTypeRepository;
    this.serviceTypeCreator = serviceTypeCreator;
  }

  public List<ServiceTypeDto> showAll() {
    return serviceTypeRepository.findAll().stream().map(ServiceType::dto).collect(Collectors.toList());
  }

  public ServiceTypeDto add(ServiceTypeDto serviceTypeDto) {
    return serviceTypeRepository.save(serviceTypeCreator.from(serviceTypeDto)).dto();
  }

  public void delete(ObjectId id) {
    serviceTypeRepository.deleteByIdOrThrow(id);
  }

  public ServiceTypeDto update(ObjectId serviceTypeId, ServiceTypeDto serviceTypeDto) {
    ServiceType oldServiceType = serviceTypeRepository.findByIdOrThrow(serviceTypeId);
    return serviceTypeRepository.updateOrThrow(serviceTypeCreator.from(serviceTypeDto, oldServiceType)).dto();
  }
}
