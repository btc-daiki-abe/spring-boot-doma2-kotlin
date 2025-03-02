package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.BZip2Data

/** MultipartFileインターフェースがwebモジュールに依存しているので、 <br></br> 本インターフェースを介させることで循環参照にならないようにする。 */
interface MultipartFileConvertible {
  fun setFilename(filename: String?)

  fun setOriginalFilename(originalFilename: String?)

  fun setContentType(contentType: String?)

  fun setContent(data: BZip2Data?)
}
