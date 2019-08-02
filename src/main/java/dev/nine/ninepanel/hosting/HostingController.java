package dev.nine.ninepanel.hosting;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.hosting.domain.HostingFacade;
import dev.nine.ninepanel.hosting.domain.dto.HostingDto;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
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

@RequestMapping(ApiLayers.HOSTINGS)
@RestController
class HostingController {

  private final HostingFacade hostingFacade;

  HostingController(HostingFacade hostingFacade) {
    this.hostingFacade = hostingFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<Page<HostingDto>> showAllHostings(Pageable pageable, @RequestParam(required = false) ObjectId userId) {
    return ResponseEntity.ok(hostingFacade.showAll(pageable, userId));
  }

  @RequiresAuthenticated
  @GetMapping("{hostingId}")
  ResponseEntity<HostingDto> showHosting(@PathVariable ObjectId hostingId) {
    return ResponseEntity.ok(hostingFacade.show(hostingId));
  }

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<HostingDto> create(@Valid @RequestBody HostingDto hostingDto) {
    return ResponseEntity.ok(hostingFacade.add(hostingDto));
  }
}
