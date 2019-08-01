package dev.nine.ninepanel.infrastructure.util;

import java.util.Arrays;
import org.springframework.core.env.Environment;

public final class EnvironmentHelper {

  private EnvironmentHelper() {
  }

  public static boolean isTestEnvironment(Environment environment) {
    return Arrays.stream(environment.getActiveProfiles()).anyMatch(s -> s.equalsIgnoreCase("test"));
  }
}
