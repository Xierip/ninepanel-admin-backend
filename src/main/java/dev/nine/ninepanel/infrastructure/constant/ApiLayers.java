package dev.nine.ninepanel.infrastructure.constant;

public final class ApiLayers {

  public static final String API_ROOT      = "/api/";
  public static final String USERS         = API_ROOT + "users";
  public static final String CLIENTS       = API_ROOT + "clients";
  public static final String SESSIONS      = API_ROOT + "sessions";
  public static final String SERVICES      = API_ROOT + "services";
  public static final String SERVICE_TYPES = API_ROOT + "service-types";
  public static final String ALERTS        = API_ROOT + "alerts";
  public static final String HOSTINGS      = API_ROOT + "hostings";
  public static final String MESSAGES      = API_ROOT + "messages";
  public static final String WEBSOCKETS    = "/ws";
  public static final String ERROR         = "/error";
  public static final String NOTIFICATIONS = "/notifications";

  private ApiLayers() {
  }

}
