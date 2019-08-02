package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.clients.domain.dto.ClientDto;
import dev.nine.ninepanel.clients.domain.exception.ClientNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ClientsFacade {

  private final ClientsRepository clientsRepository;

  public ClientsFacade(ClientsRepository clientsRepository) {
    this.clientsRepository = clientsRepository;
  }

  public Page<ClientDto> showAll(Pageable pageable) {
    return clientsRepository.findAll(pageable).map(Client::dto);
  }

  public ClientDto showById(ObjectId clientId) {
    return clientsRepository.findByIdOrThrow(clientId).dto();
  }

  public void checkIfUserExists(ObjectId clientId) {
    if (!clientsRepository.existsById(clientId)) {
      throw new ClientNotFoundException(clientId);
    }
  }
}
