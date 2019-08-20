package dev.nine.ninepanel.message;

import dev.nine.ninepanel.authentication.domain.annotation.RequiresAuthenticated;
import dev.nine.ninepanel.infrastructure.constant.ApiLayers;
import dev.nine.ninepanel.message.domain.MessageFacade;
import dev.nine.ninepanel.message.domain.dto.MessageCreationDto;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiLayers.MESSAGES)
public class MessageController {

  private final MessageFacade messageFacade;

  public MessageController(MessageFacade messageFacade) {
    this.messageFacade = messageFacade;
  }

  @RequiresAuthenticated
  @GetMapping
  ResponseEntity<?> get(Pageable pageable, @RequestParam ObjectId userId) {
    return ResponseEntity.ok(messageFacade.showAll(pageable, userId));
  }

  @RequiresAuthenticated
  @PostMapping
  ResponseEntity<?> add(@Valid @RequestBody MessageCreationDto messageCreationDto) {
    return ResponseEntity.ok(messageFacade.add(messageCreationDto));
  }

  @RequiresAuthenticated
  @PostMapping("mark-as-read")
  ResponseEntity<?> add(@RequestParam ObjectId userId) {
    messageFacade.readNewFrom(userId);
    return ResponseEntity.noContent().build();
  }

}
