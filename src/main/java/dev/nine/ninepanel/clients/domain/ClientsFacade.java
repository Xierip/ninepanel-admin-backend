package dev.nine.ninepanel.clients.domain;

public class ClientsFacade {

  private final ClientsRepository clientsRepository;

  public ClientsFacade(ClientsRepository clientsRepository) {
    this.clientsRepository = clientsRepository;
  }
}
