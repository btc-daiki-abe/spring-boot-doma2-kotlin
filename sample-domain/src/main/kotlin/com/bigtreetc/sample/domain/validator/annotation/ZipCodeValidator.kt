package com.bigtreetc.sample.domain.validator.annotation

import com.bigtreetc.sample.common.util.ValidateUtils.isEmpty
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.util.regex.Pattern

/** 入力チェック（郵便番号） */
class ZipCodeValidator : ConstraintValidator<ZipCode?, String?> {
  override fun initialize(ZipCode: ZipCode?) {}

  override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
    var isValid = false

    if (isEmpty(value)) {
      isValid = true
    } else {
      val m = p.matcher(value)

      if (m.matches()) {
        isValid = true
      }
    }

    return isValid
  }

  companion object {
    val p: Pattern = Pattern.compile("^[0-9]{7}$")
  }
}
