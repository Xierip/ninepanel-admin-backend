package dev.nine.ninepanel.user.domain;

import dev.nine.ninepanel.user.domain.dto.UserCreationDto;
import dev.nine.ninepanel.user.domain.dto.UserDto;

class UserCreator {

  User from(UserCreationDto userRegisterCommandDto) {
    return User
        .builder()
        .email(userRegisterCommandDto.getEmail().toLowerCase())
        .name(userRegisterCommandDto.getName())
        .surname(userRegisterCommandDto.getSurname())
        .password(userRegisterCommandDto.getPassword())
        .role(userRegisterCommandDto.getRole())
        .build();
  }

  User from(UserDto userDto, UserDto oldUserDto) {
    return User
        .builder()
        .id(oldUserDto.getId())
        .email(userDto.getEmail().toLowerCase())
        .name(userDto.getName())
        .surname(userDto.getSurname())
        .password(oldUserDto.getPassword())
        .role(userDto.getRole())
        .build();
  }
}
