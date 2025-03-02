package com.bigtreetc.sample.domain.dao

import java.time.LocalDateTime

/** 監査情報ホルダー */
object AuditInfoHolder {
  private val AUDIT_USER: ThreadLocal<String> = InheritableThreadLocal()

  private val AUDIT_DATE_TIME: ThreadLocal<LocalDateTime> = InheritableThreadLocal()

  /**
   * 監査情報を保存します。
   *
   * @param username
   */
  fun set(username: String, localDateTime: LocalDateTime) {
    AUDIT_USER.set(username)
    AUDIT_DATE_TIME.set(localDateTime)
  }

  val auditUser: String
    /**
     * 監査ユーザーを返します。
     *
     * @return
     */
    get() = AUDIT_USER.get()

  val auditDateTime: LocalDateTime
    /**
     * 監査時刻を返します。
     *
     * @return
     */
    get() = AUDIT_DATE_TIME.get()

  /** 監査情報をクリアします。 */
  fun clear() {
    AUDIT_USER.remove()
    AUDIT_DATE_TIME.remove()
  }
}
