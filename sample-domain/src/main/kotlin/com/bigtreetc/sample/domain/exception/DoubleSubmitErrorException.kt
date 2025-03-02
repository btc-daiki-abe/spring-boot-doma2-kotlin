package com.bigtreetc.sample.domain.exception

/** 二重送信エラー */
class DoubleSubmitErrorException : RuntimeException() {
  companion object {
    private const val serialVersionUID = -4104071331904983830L
  }
}
