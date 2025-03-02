package com.bigtreetc.sample.domain.validator.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle
import lombok.extern.slf4j.Slf4j

/** 入力チェック（日付フォーマット） */
@Slf4j
class DateFormatValidator : ConstraintValidator<DateFormat, String?> {
  private var pattern: DateTimeFormatter? = null

  override fun initialize(dateFormat: DateFormat) {
    pattern =
      DateTimeFormatter.ofPattern(dateFormat.pattern).withResolverStyle(ResolverStyle.LENIENT)
  }

  override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
    var isValid = false

    if (value == null) {
      isValid = true
    } else {
      try {
        pattern!!.parse(value)
      } catch (e: DateTimeParseException) {
        // ignore
      }
    }

    return isValid
  }
}
