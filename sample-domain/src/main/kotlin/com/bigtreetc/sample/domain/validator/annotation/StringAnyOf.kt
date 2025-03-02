package com.bigtreetc.sample.domain.validator.annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

/** 入力チェック（指定した値のいずれか） */
@MustBeDocumented
@Constraint(validatedBy = [StringAnyOfValidator::class])
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringAnyOf(
  val values: Array<String> = [],
  val message: String = "{validator.annotation.StringAnyOf.message}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<Any>> = [],
) {
  @Target(AnnotationTarget.FIELD)
  @Retention(AnnotationRetention.RUNTIME)
  @MustBeDocumented
  annotation class List(vararg val value: StringAnyOf)
}
