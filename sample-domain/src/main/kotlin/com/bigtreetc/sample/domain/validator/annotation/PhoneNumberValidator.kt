package com.bigtreetc.sample.domain.validator.annotation

import com.bigtreetc.sample.common.util.ValidateUtils.isEmpty
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory

/** 入力チェック（電話番号） */
@Slf4j
class PhoneNumberValidator : ConstraintValidator<PhoneNumber, String?> {
  private val log = LoggerFactory.getLogger(javaClass)
  private var pattern: Pattern? = null

  override fun initialize(phoneNumber: PhoneNumber) {
    try {
      pattern = Pattern.compile(phoneNumber.regexp)
    } catch (e: PatternSyntaxException) {
      log.error("invalid regular expression.", e)
      throw e
    }
  }

  override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
    var isValid = false

    if (isEmpty(value)) {
      isValid = true
    } else {
      val m = pattern!!.matcher(value)

      if (m.matches()) {
        isValid = true
      }
    }

    return isValid
  }
}
