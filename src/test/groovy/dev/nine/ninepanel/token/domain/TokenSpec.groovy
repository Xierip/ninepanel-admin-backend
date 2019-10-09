package dev.nine.ninepanel.token.domain


import dev.nine.ninepanel.base.IntegrationSpec
import dev.nine.ninepanel.token.domain.dto.TokenDto
import dev.nine.ninepanel.token.domain.exception.TokenNotFoundException
import org.springframework.beans.factory.annotation.Autowired

class TokenSpec extends IntegrationSpec implements SampleTokens {

  @Autowired
  private TokenFacade tokenFacade

  def "should show token"() {
    given: "system has adminToken"
      TokenDto tokenDto = tokenFacade.addToken(randomRefreshToken)
    expect: "system returns adminToken"
      tokenFacade.getTokenByBody(tokenDto.body).id == tokenDto.id
  }

  def "should show a list of tokens"() {
    given: "system has two tokens for the same user"
      TokenDto tokenDto1 = tokenFacade.addToken(randomRefreshToken)
      TokenDto tokenDto2 = tokenFacade.addToken(randomRefreshToken)

    expect: "system returns list of two tokens for given user"
      tokenFacade.getTokensByUserId(tokenDto1.userId).size() == 2
  }

  def "should delete token"() {
    given: "system has adminToken"
      TokenDto tokenDto = tokenFacade.addToken(randomRefreshToken)
    and: "we delete the adminToken"
      tokenFacade.deleteToken(tokenDto.body)
    when: "system is asked for the adminToken"
      tokenFacade.getTokenByBody(tokenDto.body)
    then: "should throw exception"
      thrown(TokenNotFoundException)
  }

  def "should throw exception when asked for token that's not in the system"() {
    when: "system is asked for a adminToken that doesn't exist"
      tokenFacade.getTokenByBody("hawajskaToZłaPizza")
    then: "should throw exception"
      thrown(TokenNotFoundException)
  }

}
