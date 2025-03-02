package com.bigtreetc.sample.domain.validator.annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

/** 入力チェック（電話番号） */
@MustBeDocumented
@Constraint(validatedBy = [PhoneNumberValidator::class])
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class PhoneNumber(
  val regexp: String = "^[0-9]{1,4}-[0-9]{1,4}-[0-9]{4}$",
  val message: String = "{validator.annotation.PhoneNumber.message}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<Any>> = [],
) {
  @Target(AnnotationTarget.FIELD)
  @Retention(AnnotationRetention.RUNTIME)
  @MustBeDocumented
  annotation class List(vararg val value: PhoneNumber)
}
