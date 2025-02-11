package com.bigtreetc.sample.common.util

import java.util.*
import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable
import org.springframework.context.i18n.LocaleContextHolder

object MessageUtils {

  lateinit var messageSource: MessageSource

  fun init(messageSource: MessageSource) {
    this.messageSource = messageSource
  }

  /**
   * メッセージを取得します。
   *
   * @param key
   * @param args
   * @return
   */
  fun getMessage(key: String?, vararg args: Any?): String {
    val locale = LocaleContextHolder.getLocale()
    return messageSource!!.getMessage(key, args, locale)
  }

  /**
   * ロケールを指定してメッセージを取得します。
   *
   * @param key
   * @param locale
   * @param args
   * @return
   */
  fun getMessage(key: String?, locale: Locale?, vararg args: Any?): String {
    return messageSource!!.getMessage(key, args, locale)
  }

  /**
   * メッセージを取得します。
   *
   * @param resolvable
   * @return
   */
  fun getMessage(resolvable: MessageSourceResolvable?): String {
    val locale = LocaleContextHolder.getLocale()
    return messageSource!!.getMessage(resolvable, locale)
  }
}
