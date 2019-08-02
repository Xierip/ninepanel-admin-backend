package dev.nine.ninepanel.clients.domain;

import dev.nine.ninepanel.clients.domain.dto.ClientDto;

public class ClientCreator {

  Client from(ClientDto clientDto, Client oldClient) {
    return Client.builder()
        .id(oldClient.getId())
        .address(clientDto.getAddressDetails())
        .companyDetails(clientDto.getCompanyDetails())
        .email(clientDto.getEmail())
        .name(clientDto.getName())
        .password(oldClient.getPassword())
        .phoneNumber(clientDto.getPhoneNumber())
        .surname(clientDto.getSurname())
        .build();
  }

}
