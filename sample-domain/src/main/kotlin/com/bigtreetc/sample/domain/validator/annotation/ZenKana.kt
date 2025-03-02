package com.bigtreetc.sample.domain.validator.annotation

import jakarta.validation.Constraint
import kotlin.reflect.KClass

/** 入力チェック（全角カナ） */
@MustBeDocumented
@Constraint(validatedBy = [ZenKanaValidator::class])
@Target(
  AnnotationTarget.FUNCTION,
  AnnotationTarget.PROPERTY_GETTER,
  AnnotationTarget.PROPERTY_SETTER,
  AnnotationTarget.FIELD,
)
@Retention(AnnotationRetention.RUNTIME)
annotation class ZenKana(
  val message: String = "{validator.annotation.ZenKana.message}",
  val groups: Array<KClass<*>> = [],
  val payload: Array<KClass<Any>> = [],
) {
  @Target(AnnotationTarget.FIELD)
  @Retention(AnnotationRetention.RUNTIME)
  @MustBeDocumented
  annotation class List(vararg val value: ZenKana)
}
