package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.BZip2Data
import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import org.seasar.doma.*

@Table(name = "upload_files")
@Entity
@Getter
open class UploadFile : DomaEntityImpl(), MultipartFileConvertible {
  @OriginalStates
  var originalStates: // 差分UPDATEのために定義する
    UploadFile? =
    null

  @Id
  @Column(name = "upload_file_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // ファイル名
  @Column(name = "file_name") var filename: String? = null

  // オリジナルファイル名
  @Column(name = "original_file_name") var originalFilename: String? = null

  // コンテンツタイプ
  var contentType: String? = null

  // コンテンツ
  var content: BZip2Data? = null

  fun setId(id: Long) {
    this.id = id
  }

  override fun setFilename(filename: String?) {
    this.filename = filename
  }

  override fun setOriginalFilename(originalFilename: String?) {
    this.originalFilename = originalFilename
  }

  override fun setContentType(contentType: String?) {
    this.contentType = contentType
  }

  override fun setContent(data: BZip2Data?) {
    this.content = data
  }
}
