package com.bigtreetc.sample.domain.exception

import java.util.*
import org.springframework.validation.Errors

/** バリデーションエラー */
class ValidationErrorException(errors: Errors?) : RuntimeException() {
  val errors: Optional<Errors> = Optional.ofNullable(errors)

  companion object {
    private const val serialVersionUID = 5084588189148251787L
  }
}
