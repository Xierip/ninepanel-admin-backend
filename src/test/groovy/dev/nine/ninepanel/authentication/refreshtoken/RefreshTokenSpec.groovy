package dev.nine.ninepanel.authentication.refreshtoken

import dev.nine.ninepanel.base.IntegrationSpec
import org.springframework.beans.factory.annotation.Autowired

class RefreshTokenSpec extends IntegrationSpec implements RefreshTokenData {

  @Autowired
  RefreshTokenFacade refreshTokenFacade

  def "should limit max refresh tokens"() {
    given: "user has max distinct refresh tokens in the system"
      for (int i = 1; i <= refreshTokenProperties.getToken().getLimit(); ++i) {
        refreshTokenFacade.create(someUserId, "device" + i)
      }
    when: "i add a token to the system"
      refreshTokenFacade.create(someUserId, "brandNewDevice")
    then: "there should still be the max amount of requests in the system"
      refreshTokenFacade.getUserTokens(someUserId).size() == refreshTokenProperties.getToken().getLimit()
  }

  def "should refresh same deviceId refresh token"() {
    given: "there is a token in the system"
      refreshTokenFacade.create(someUserId, "device")
    when: "i add a new token with the same deviceId"
      refreshTokenFacade.create(someUserId, "device")
    then: "there should be one token in the system"
      refreshTokenFacade.getUserTokens(someUserId).size() == 1
  }

}
