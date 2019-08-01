package dev.nine.ninepanel.hosting.domain;

import dev.nine.ninepanel.hosting.domain.dto.HostingDto;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

public class HostingFacade {

  private final HostingRepository hostingRepository;

  HostingFacade(HostingRepository hostingRepository) {
    this.hostingRepository = hostingRepository;
  }

  public List<HostingDto> showAllForClient(ObjectId clientId) {
    return hostingRepository.findAllByClientId(clientId).stream().map(Hosting::dto).collect(Collectors.toList());
  }

  public HostingDto showForClient(ObjectId serviceId, ObjectId clientId) {
    return hostingRepository.findByIdAndClientIdOrThrow(serviceId, clientId).dto();
  }
}
