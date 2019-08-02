package dev.nine.ninepanel.clients;

import dev.nine.ninepanel.clients.domain.ClientsFacade;
import dev.nine.ninepanel.clients.domain.dto.ClientDto;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiLayers.CLIENTS)
@RestController
class ClientsController {

  private ClientsFacade clientsFacade;

  @GetMapping
  ResponseEntity<Page<ClientDto>> showAll(Pageable pageable) {
    return ResponseEntity.ok(clientsFacade.showAll(pageable));
  }

  @GetMapping("{clientId}")
  ResponseEntity<ClientDto> show(@RequestParam ObjectId clientId) {
    return ResponseEntity.ok(clientsFacade.showById(clientId));
  }

  @DeleteMapping("{clientId}")
  ResponseEntity<?> delete(@PathVariable ObjectId clientId) {
    clientsFacade.delete(clientId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("{clientId}")
  ResponseEntity<?> update(@PathVariable ObjectId clientId, ClientDto clientDto) {
    return ResponseEntity.ok(clientsFacade.update(clientId, clientDto));
  }
}
