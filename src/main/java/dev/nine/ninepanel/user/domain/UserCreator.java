package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.user.domain.dto.SignUpDto;

class UserCreator {

  User from(SignUpDto userRegisterCommandDto) {
    return User
        .builder()
        .email(userRegisterCommandDto.getEmail().toLowerCase())
        .name(userRegisterCommandDto.getName())
        .surname(userRegisterCommandDto.getSurname())
        .password(userRegisterCommandDto.getPassword())
        .build();
  }
}
