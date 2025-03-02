package com.bigtreetc.sample.domain.validator.annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

/** 入力チェック（日付フォーマット） */
@MustBeDocumented
@Constraint(validatedBy = [DateFormatValidator::class])
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class DateFormat(
  val pattern: String = "yyyy/MM/dd HH:mm:ss",
  val message: String = "{validator.annotation.DateFormat.message}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<Any>> = [],
) {
  @Target(AnnotationTarget.FIELD)
  @Retention(AnnotationRetention.RUNTIME)
  @MustBeDocumented
  annotation class List(vararg val value: DateFormat)
}
