package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.user.domain.dto.UserCreationDto;

class UserCreator {

  User from(UserCreationDto userRegisterCommandDto) {
    return User
        .builder()
        .email(userRegisterCommandDto.getEmail().toLowerCase())
        .name(userRegisterCommandDto.getName())
        .surname(userRegisterCommandDto.getSurname())
        .password(userRegisterCommandDto.getPassword())
        .build();
  }
}
