package dev.nine.ninepanel.authentication.refreshtoken

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

trait RefreshTokenData {
  @Autowired
  RefreshTokenProperties refreshTokenProperties

  final ObjectId someUserId = new ObjectId()

}