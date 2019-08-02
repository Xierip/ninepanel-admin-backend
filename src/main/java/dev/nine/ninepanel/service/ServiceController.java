package dev.nine.ninepanel.service;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.service.domain.ServiceFacade;
import dev.nine.ninepanel.service.domain.dto.ServiceDto;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiLayers.SERVICES)
@RestController
class ServiceController {

  private final ServiceFacade serviceFacade;

  public ServiceController(ServiceFacade serviceFacade) {
    this.serviceFacade = serviceFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<Page<ServiceDto>> showAllServices(Pageable pageable, @RequestParam(required = false) ObjectId userId) {
    return ResponseEntity.ok(serviceFacade.showAll(pageable, userId));
  }

  @RequiresAuthenticated
  @GetMapping("{serviceId}")
  ResponseEntity<ServiceDto> showService(@PathVariable ObjectId serviceId) {
    return ResponseEntity.ok(serviceFacade.show(serviceId));
  }

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<ServiceDto> create(@RequestBody @Valid ServiceDto serviceDto) {
    return ResponseEntity.ok(serviceFacade.add(serviceDto));
  }
}
