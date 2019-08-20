package dev.nine.ninepanel.infrastructure.util;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class PayloadParser {

  private PayloadParser() {
  }

  public static <T> T parseSingleField(Map<String, Object> payload, String fieldName, Class<? extends T> fieldType) {
    if (!payload.containsKey(fieldName)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    return convertFieldType(payload.get(fieldName), fieldType);
  }

  private static <T> T convertFieldType(Object field, Class<? extends T> fieldType) {
    try {
      return fieldType.cast(field);
    } catch (ClassCastException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
