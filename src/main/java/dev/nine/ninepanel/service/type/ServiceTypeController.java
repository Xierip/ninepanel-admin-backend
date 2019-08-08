package dev.nine.ninepanel.service.type;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.service.type.domain.ServiceTypeFacade;
import dev.nine.ninepanel.service.type.domain.dto.ServiceTypeDto;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.SERVICE_TYPES)
class ServiceTypeController {

  private final ServiceTypeFacade serviceTypeFacade;

  ServiceTypeController(ServiceTypeFacade serviceTypeFacade) {
    this.serviceTypeFacade = serviceTypeFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<?> getServiceTypes() {
    return ResponseEntity.ok(serviceTypeFacade.showAll());
  }

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<?> addServiceType(@RequestBody @Valid ServiceTypeDto serviceTypeDto) {
    return ResponseEntity.ok(serviceTypeFacade.add(serviceTypeDto));
  }

  @RequiresAuthenticated
  @DeleteMapping("{id}")
  ResponseEntity<?> deleteServiceType(@PathVariable ObjectId id) {
    serviceTypeFacade.delete(id);
    return ResponseEntity.noContent().build();
  }

  @RequiresAuthenticated
  @PutMapping("{id}")
  ResponseEntity<?> updateServiceType(@PathVariable ObjectId id, @RequestBody @Valid ServiceTypeDto serviceTypeDto) {
    return ResponseEntity.ok(serviceTypeFacade.update(id, serviceTypeDto));
  }

}
