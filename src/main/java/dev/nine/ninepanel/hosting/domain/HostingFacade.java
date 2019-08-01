package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.hosting.domain.dto.HostingDto;
import dev.nine.ninepanel.user.domain.UserFacade;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class HostingFacade {

  private final HostingRepository hostingRepository;
  private       HostingCreator    hostingCreator;
  private       UserFacade        userFacade;

  HostingFacade(HostingRepository hostingRepository, HostingCreator hostingCreator, UserFacade userFacade) {
    this.hostingRepository = hostingRepository;
    this.hostingCreator = hostingCreator;
    this.userFacade = userFacade;
  }


  public HostingDto showForClient(ObjectId serviceId, ObjectId clientId) {
    return hostingRepository.findByIdAndClientIdOrThrow(serviceId, clientId).dto();
  }

  public Page<HostingDto> showAll(Pageable pageable, ObjectId userId) {
    if (userId != null) {
      return hostingRepository.findAllByClientId(userId, pageable).map(Hosting::dto);
    }
    return hostingRepository.findAll(pageable).map(Hosting::dto);
  }

  public HostingDto show(ObjectId serviceId) {
    return hostingRepository.findByIdOrThrow(serviceId).dto();
  }

  public HostingDto add(HostingDto hostingDto) {
    userFacade.showUserById(hostingDto.getClientId());
    return hostingRepository.save(hostingCreator.from(hostingDto)).dto();
  }
}
