package dev.nine.ninepanel.message;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.message.domain.MessageFacade;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.MESSAGES)
class MessageController {

  private final MessageFacade messageFacade;

  public MessageController(MessageFacade messageFacade) {
    this.messageFacade = messageFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<?> get(Pageable pageable, @RequestParam ObjectId userId) {
    return ResponseEntity.ok(messageFacade.showAll(pageable, userId));
  }

}
