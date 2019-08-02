package dev.nine.ninepanel.user.domain

import dev.nine.ninepanel.user.domain.dto.UserCreationDto
import dev.nine.ninepanel.user.domain.dto.UserDto

trait SampleUsers {

  UserDto sampleUser = createUserDto("example@test.com",
      "pass123456",
      "Andrzej",
      "Kowalski")
  UserCreationDto sampleSignUpDto = new UserCreationDto(
      "test123@test.com",
      "password123",
      "Test",
      "testCaptcha"
  )
  
  static private createUserDto(String email, String password, String name, String surname) {
    return UserDto.builder()
        .email(email)
        .password(password)
        .name(name)
        .surname(surname)
        .build()
  }

}