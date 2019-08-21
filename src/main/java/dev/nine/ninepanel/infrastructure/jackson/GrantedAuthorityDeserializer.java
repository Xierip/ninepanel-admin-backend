package dev.nine.ninepanel.infrastructure.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityDeserializer extends StdDeserializer<GrantedAuthority> {

  public GrantedAuthorityDeserializer(Class<?> vc) {
    super(vc);
  }

  public GrantedAuthorityDeserializer() {
    this(null);
  }

  @Override
  public GrantedAuthority deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
    return new SimpleGrantedAuthority(jsonParser.getText());
  }
}
