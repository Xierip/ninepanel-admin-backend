package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.clients.domain.dto.ClientDto;
import dev.nine.ninepanel.clients.domain.exception.ClientNotFoundException;
import dev.nine.ninepanel.token.domain.TokenFacade;
import dev.nine.ninepanel.token.domain.TokenType;
import dev.nine.ninepanel.token.domain.dto.TokenDto;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ClientsFacade {

  private final ClientsRepository clientsRepository;
  private final ClientCreator     clientCreator;
  private final TokenFacade       tokenFacade;

  ClientsFacade(ClientsRepository clientsRepository, ClientCreator clientCreator, TokenFacade tokenFacade) {
    this.clientsRepository = clientsRepository;
    this.clientCreator = clientCreator;
    this.tokenFacade = tokenFacade;
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

  public List<ClientDto> showAllMatching(String searchTerm) {
    return clientsRepository.findAllByDisplayNameLikeOrEmailLikeOrPhoneNumberLike(searchTerm, searchTerm, searchTerm)
        .stream()
        .map(Client::dto)
        .collect(Collectors.toList());
  }

  public ClientDto showByStaffCode(String code) {
    TokenDto token = tokenFacade.getTokenByBodyAndTokenType(code, TokenType.STAFF_TOKEN);
    return showById(token.getUserId());
  }
}
