package dev.nine.ninepanel.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDto {

  private int    code;
  private String message;

}
