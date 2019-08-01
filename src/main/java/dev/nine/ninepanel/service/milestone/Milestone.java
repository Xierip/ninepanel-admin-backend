package dev.nine.ninepanel.service.milestone;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

  private String        name;
  private boolean       completed;
  private LocalDateTime completedDate;

}
