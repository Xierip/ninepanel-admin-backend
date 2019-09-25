package dev.nine.ninepanel.clients.domain;


import dev.nine.ninepanel.clients.domain.dto.AvatarDto;

enum UserAvatar {

  DEFAULT(""),
  VICTORIA_SECRET_ANGEL("icons8-victoria-secret-angel.svg"),
  SCREAM("icons8-scream.svg"),
  POPEYE("icons8-popeye.svg"),
  OLIVE_OYL("icons8-olive-oyl.svg"),
  NINJA_TURTLE("icons8-ninja-turtle.svg"),
  IRON_MAN("icons8-iron-man.svg"),
  GENIE("icons8-genie.svg"),
  CHUCKY("icons8-chucky.svg"),
  BRUTUS("icons8-brutus.svg"),
  BATMAN_LOGO("icons8-batman-logo.svg");

  private final AvatarDto dto;

  UserAvatar(String fileName) {
    this.dto = new AvatarDto(this.name(), "https://panel.nine.dev/avatars/" + fileName);
  }

  public AvatarDto dto() {
    return this.dto;
  }

}
