package com.bigtreetc.sample.domain.entity.common

import com.bigtreetc.sample.common.util.CompressUtils.compress
import com.bigtreetc.sample.common.util.CompressUtils.decompress
import java.io.Serializable
import java.util.*
import lombok.NoArgsConstructor
import org.seasar.doma.Domain

@Domain(valueType = ByteArray::class)
@NoArgsConstructor
class BZip2Data internal constructor(bytes: ByteArray?) : Serializable {
  var data: ByteArray

  var bzip2: ByteArray? = null

  // ResultSet.getBytes(int)で取得された値がこのコンストラクタで設定される
  init {
    data = decompress(bytes)
  }

  val value: ByteArray
    // PreparedStatement.setBytes(int, bytes)へ設定する値がこのメソッドから取得される
    get() {
      if (Objects.isNull(bzip2)) {
        bzip2 = compress(data)
      }
      return bzip2 as ByteArray
    }

  fun toBase64(): String {
    return Base64.getEncoder().encodeToString(data)
  }

  companion object {
    private const val serialVersionUID = -4805556024192461766L

    /**
     * ファクトリメソッド
     *
     * @param bytes
     * @return
     */
    fun of(bytes: ByteArray): BZip2Data {
      val bZip2Data = BZip2Data(bytes = null)
      bZip2Data.data = bytes
      return bZip2Data
    }
  }
}
