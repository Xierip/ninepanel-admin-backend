package dev.nine.ninepanel.clients;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.clients.domain.dto.ClientDto;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiLayers.CLIENTS)
@RestController
class ClientsController {

  private ClientsFacade clientsFacade;

  ClientsController(ClientsFacade clientsFacade) {
    this.clientsFacade = clientsFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<?> showAll(Pageable pageable, @RequestParam(required = false) String search, @RequestParam(required = false) String code) {
    if (search != null) {
      return ResponseEntity.ok(clientsFacade.showAllMatching(search));
    }
    if (code != null) {
      return ResponseEntity.ok(clientsFacade.showByStaffCode(code));
    }
    return ResponseEntity.ok(clientsFacade.showAll(pageable));
  }

  @RequiresAuthenticated
  @GetMapping("{clientId}")
  ResponseEntity<ClientDto> show(@PathVariable ObjectId clientId) {
    return ResponseEntity.ok(clientsFacade.showById(clientId));
  }

  @RequiresAuthenticated
  @DeleteMapping("{clientId}")
  ResponseEntity<?> delete(@PathVariable ObjectId clientId) {
    clientsFacade.delete(clientId);
    return ResponseEntity.noContent().build();
  }

  @RequiresAuthenticated
  @PutMapping("{clientId}")
  ResponseEntity<?> update(@PathVariable ObjectId clientId, @RequestBody @Valid ClientDto clientDto) {
    return ResponseEntity.ok(clientsFacade.update(clientId, clientDto));
  }
}
