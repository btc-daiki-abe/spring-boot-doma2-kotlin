package com.bigtreetc.sample.domain.entity.common

import java.io.Serializable
import lombok.Getter
import lombok.NoArgsConstructor
import org.seasar.doma.Domain

@Domain(valueType = String::class, factoryMethod = "of")
@NoArgsConstructor
class CommaSeparatedString internal constructor(data: String?) : Serializable {
  // PreparedStatement.setBytes(int, bytes)へ設定する値がこのメソッドから取得される
  @Getter var value: String?

  val splitString: Array<String>?
    get() {
      if (this.value == null) {
        return null
      }
      return value!!.split(COMMA.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

  // ResultSet.getBytes(int)で取得された値がこのコンストラクタで設定される
  init {
    this.value = java.lang.String.join(COMMA, data)
  }

  companion object {
    private const val serialVersionUID = -6864852815920199569L

    private const val COMMA = ","

    /**
     * ファクトリメソッド
     *
     * @param data
     * @return
     */
    fun of(data: String?): CommaSeparatedString {
      val css = CommaSeparatedString(data = null)
      css.value = java.lang.String.join(",", data)
      return css
    }
  }
}
