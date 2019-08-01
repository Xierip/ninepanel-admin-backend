package dev.nine.ninepanel.service;

import dev.nine.ninepanel.authentication.domain.annotation.AuthenticatedUser;
import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.service.domain.ServiceFacade;
import dev.nine.ninepanel.service.domain.dto.ServiceDto;
import dev.nine.ninepanel.user.domain.UserHelper;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
  ResponseEntity<List<ServiceDto>> showAllServices(@AuthenticatedUser UserDetails userDetails) {
    return ResponseEntity.ok(serviceFacade.showAllForClient(UserHelper.getUserId(userDetails)));
  }

  @RequiresAuthenticated
  @GetMapping("{serviceId}")
  ResponseEntity<ServiceDto> showService(@AuthenticatedUser UserDetails userDetails, @PathVariable ObjectId serviceId) {
    return ResponseEntity.ok(serviceFacade.showForClient(serviceId, UserHelper.getUserId(userDetails)));
  }
}
