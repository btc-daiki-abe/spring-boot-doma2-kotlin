package com.bigtreetc.sample.domain.validator.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.*
import lombok.extern.slf4j.Slf4j

/** 入力チェック（指定した値のいずれか） */
@Slf4j
class StringAnyOfValidator : ConstraintValidator<StringAnyOf, String?> {
  private lateinit var values: Array<String>

  override fun initialize(anyOf: StringAnyOf) {
    values = anyOf.values
  }

  override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
    val isValid =
      if (value == null) {
        true
      } else {
        Arrays.asList(*values).contains(value)
      }

    return isValid
  }
}
