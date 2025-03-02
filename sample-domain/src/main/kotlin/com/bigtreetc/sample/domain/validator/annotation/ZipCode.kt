package com.bigtreetc.sample.domain.validator.annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

/** 入力チェック（郵便番号） */
@MustBeDocumented
@Constraint(validatedBy = [ZipCodeValidator::class])
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ZipCode(
  val message: String = "{validator.annotation.ZipCode.message}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<Any>> = [],
) {
  @Target(AnnotationTarget.FIELD)
  @Retention(AnnotationRetention.RUNTIME)
  @MustBeDocumented
  annotation class List(vararg val value: ZipCode)
}
