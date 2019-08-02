package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.clients.domain.dto.ClientDto;
import dev.nine.ninepanel.clients.domain.exception.ClientNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ClientsFacade {

  private final ClientsRepository clientsRepository;
  private final ClientCreator     clientCreator;

  public ClientsFacade(ClientsRepository clientsRepository, ClientCreator clientCreator) {
    this.clientsRepository = clientsRepository;
    this.clientCreator = clientCreator;
  }

  public Page<ClientDto> showAll(Pageable pageable) {
    return clientsRepository.findAll(pageable).map(Client::dto);
  }

  public ClientDto showById(ObjectId clientId) {
    return clientsRepository.findByIdOrThrow(clientId).dto();
  }

  public void checkIfExists(ObjectId clientId) {
    if (!clientsRepository.existsById(clientId)) {
      throw new ClientNotFoundException(clientId);
    }
  }

  public void delete(ObjectId clientId) {
    clientsRepository.deleteByIdOrThrow(clientId);
  }

  public ClientDto update(ObjectId clientId, ClientDto clientDto) {
    Client oldClient = clientsRepository.findByIdOrThrow(clientId);
    return clientsRepository.save(clientCreator.from(clientDto, oldClient)).dto();
  }
}
