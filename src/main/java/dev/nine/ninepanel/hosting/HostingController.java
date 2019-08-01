package dev.nine.ninepanel.hosting;

import dev.nine.ninepanel.authentication.domain.annotation.AuthenticatedUser;
import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.hosting.domain.HostingFacade;
import dev.nine.ninepanel.hosting.domain.dto.HostingDto;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.user.domain.UserHelper;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiLayers.HOSTINGS)
@RestController
class HostingController {

  private final HostingFacade hostingFacade;

  HostingController(HostingFacade hostingFacade) {
    this.hostingFacade = hostingFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<List<HostingDto>> showAllHostings(@AuthenticatedUser UserDetails userDetails) {
    return ResponseEntity.ok(hostingFacade.showAllForClient(UserHelper.getUserId(userDetails)));
  }

  @RequiresAuthenticated
  @GetMapping("{hostingId}")
  ResponseEntity<HostingDto> showHosting(@AuthenticatedUser UserDetails userDetails, @PathVariable ObjectId hostingId) {
    return ResponseEntity.ok(hostingFacade.showForClient(hostingId, UserHelper.getUserId(userDetails)));
  }
}
