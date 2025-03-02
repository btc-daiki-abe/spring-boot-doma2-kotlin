package com.bigtreetc.sample.domain

import java.time.format.DateTimeFormatter

/** 定数定義 */
object Const {
  // yyyy/MM/dd HH:mm:ss
  val YYYY_MM_DD_HHmmss: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
}
