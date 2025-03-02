package com.bigtreetc.sample.domain.dao

/** 二重送信防止チェックトークンホルダー */
object DoubleSubmitCheckTokenHolder {
  private val EXPECTED_TOKEN: ThreadLocal<String> = InheritableThreadLocal()

  private val ACTUAL_TOKEN: ThreadLocal<String> = InheritableThreadLocal()

  /**
   * トークンを保存します。
   *
   * @param expected
   * @param actual
   */
  fun set(expected: String, actual: String) {
    EXPECTED_TOKEN.set(expected)
    ACTUAL_TOKEN.set(actual)
  }

  val expectedToken: String
    /**
     * セッションに保存されていたトークンを返します。
     *
     * @return
     */
    get() = EXPECTED_TOKEN.get()

  val actualToken: String
    /**
     * 画面から渡ってきたトークンを返します。
     *
     * @return
     */
    get() = ACTUAL_TOKEN.get()

  /** 監査情報をクリアします。 */
  fun clear() {
    EXPECTED_TOKEN.remove()
    ACTUAL_TOKEN.remove()
  }
}
