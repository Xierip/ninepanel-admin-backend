package dev.nine.ninepanel.alert.domain;

import dev.nine.ninepanel.alert.domain.dto.AlertDto;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

public class AlertFacade {

  private AlertRepository alertRepository;
  private AlertCreator    alertCreator;

  AlertFacade(AlertRepository alertRepository,
      AlertCreator alertCreator) {
    this.alertRepository = alertRepository;
    this.alertCreator = alertCreator;
  }

  public List<AlertDto> showAll() {
    return alertRepository.findAll().stream().map(Alert::dto).collect(Collectors.toList());
  }

  public AlertDto add(AlertDto alertDto) {
    return alertRepository.save(alertCreator.fromDto(alertDto)).dto();
  }

  public void delete(ObjectId id) {
    alertRepository.deleteByIdOrThrow(id);
  }
}
