package com.bigtreetc.sample.common.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/** 日付ユーティリティ */
object DateUtils {

  /**
   * Date型の値を指定されたDateTimeFormatterフォーマットした値を返します。
   *
   * @param fromDate
   * @param formatter
   * @return
   */
  fun format(fromDate: Date, formatter: DateTimeFormatter): String {
    val zoneId = zoneId
    val localDateTime = fromDate.toInstant().atZone(zoneId).toLocalDateTime()
    val result = formatter.format(localDateTime)
    return result
  }

  /**
   * LocalDateTime型の値を指定されたDateTimeFormatterフォーマットした値を返します。
   *
   * @param fromLocalDateTime
   * @param formatter
   * @return
   */
  fun format(fromLocalDateTime: LocalDateTime?, formatter: DateTimeFormatter): String {
    val result = formatter.format(fromLocalDateTime)
    return result
  }

  /**
   * Date型の値をLocalDateTime型に変換して返します。
   *
   * @param fromDate
   * @return
   */
  fun toLocalDateTime(fromDate: Date): LocalDateTime {
    val zoneId = zoneId
    return fromDate.toInstant().atZone(zoneId).toLocalDateTime()
  }

  /**
   * LocalDateTime型の値をDate型に変換して返します。
   *
   * @param fromLocalDateTime
   * @return
   */
  fun toDate(fromLocalDateTime: LocalDateTime?): Date {
    val zoneId = zoneId
    val zoneDateTime = ZonedDateTime.of(fromLocalDateTime, zoneId)
    return Date.from(zoneDateTime.toInstant())
  }

  /**
   * LocalDate型の値をDate型に変換して返します。
   *
   * @param localDate
   * @return
   */
  fun toDate(localDate: LocalDate): Date {
    val zoneId = zoneId
    val zoneDateTime = localDate.atStartOfDay(zoneId).toInstant()
    return Date.from(zoneDateTime)
  }

  internal val zoneId: ZoneId
    get() = ZoneId.systemDefault()
}
