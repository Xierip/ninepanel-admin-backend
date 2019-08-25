package dev.nine.ninepanel.websockets.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;

class ChannelUserMatcher {

  boolean matches(StompPrincipal user, String destination) {

    ObjectId authenticatedUserId = user.getId();

    Pattern pattern = Pattern.compile("\\.(.*)");
    Matcher matcher = pattern.matcher(destination);

    if (matcher.find()) {
      String channelUserIdString = matcher.group(1);
      String authenticatedUserIdString = authenticatedUserId.toHexString();

      return channelUserIdString.equals(authenticatedUserIdString);
    }

    return false;
  }

}
