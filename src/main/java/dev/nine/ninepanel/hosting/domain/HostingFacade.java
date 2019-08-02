package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.hosting.domain.dto.HostingDto;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class HostingFacade {

  private final HostingRepository hostingRepository;
  private       HostingCreator    hostingCreator;
  private       ClientsFacade     clientsFacade;

  HostingFacade(HostingRepository hostingRepository, HostingCreator hostingCreator, ClientsFacade clientsFacade) {
    this.hostingRepository = hostingRepository;
    this.hostingCreator = hostingCreator;
    this.clientsFacade = clientsFacade;
  }

  public HostingDto showForClient(ObjectId hostingId, ObjectId clientId) {
    return hostingRepository.findByIdAndClientIdOrThrow(hostingId, clientId).dto();
  }

  public Page<HostingDto> showAll(Pageable pageable, ObjectId userId) {
    if (userId != null) {
      return hostingRepository.findAllByClientId(userId, pageable).map(Hosting::dto);
    }
    return hostingRepository.findAll(pageable).map(Hosting::dto);
  }

  public HostingDto show(ObjectId hostingId) {
    return hostingRepository.findByIdOrThrow(hostingId).dto();
  }

  public HostingDto add(HostingDto hostingDto) {
    clientsFacade.checkIfExists(hostingDto.getClientId());
    return hostingRepository.save(hostingCreator.from(hostingDto)).dto();
  }

  public HostingDto update(ObjectId hostingId, HostingDto hostingDto) {
    clientsFacade.checkIfExists(hostingDto.getClientId());
    Hosting oldHosting = hostingRepository.findByIdOrThrow(hostingId);
    return hostingRepository.updateOrThrow(hostingCreator.from(hostingDto, oldHosting)).dto();
  }

  public void delete(ObjectId hostingId) {
    hostingRepository.deleteByIdOrThrow(hostingId);
  }
}
