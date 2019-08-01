package dev.nine.ninepanel.user.domain

import dev.nine.ninepanel.user.addressdetails.AddressDetails
import dev.nine.ninepanel.user.agreementsdetails.AgreementsDetails
import dev.nine.ninepanel.user.domain.dto.SignUpDto
import dev.nine.ninepanel.user.domain.dto.UserDto

trait SampleUsers {

  UserDto sampleUser = createUserDto("example@test.com",
      "pass123456",
      "Andrzej",
      "Kowalski")
  SignUpDto sampleSignUpDto = new SignUpDto(
      "test123@test.com",
      "password123",
      "Test",
      "Test",
      "123 456 789",
      new AddressDetails("test", "test", "test", "test"),
      new AgreementsDetails(true),
      null,
      "testCaptcha"
  )
  
  static private createUserDto(String email, String password, String name, String surname) {
    return UserDto.builder()
        .email(email)
        .addressDetails(
        new AddressDetails("test", "test", "test", "test")
    )
        .password(password)
        .name(name)
        .surname(surname)
        .phoneNumber("123 456 789")
        .build()
  }

}