package dev.nine.ninepanel.message;

import dev.nine.ninepanel.authentication.domain.annotation.AuthenticatedUser;
import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.message.domain.MessageFacade;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.MESSAGES)
class MessageController {

  private final MessageFacade messageFacade;

  MessageController(MessageFacade messageFacade) {
    this.messageFacade = messageFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<Map<String, Object>> get(Pageable pageable, @RequestParam(required = false) ObjectId userId,
      @AuthenticatedUser UserDetails userDetails) {
    return ResponseEntity.ok(Map.of(
        "conversations", this.messageFacade.showAll(userId))
    );
  }

}
