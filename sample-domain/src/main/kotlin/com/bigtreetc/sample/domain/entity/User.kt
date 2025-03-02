package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import kotlin.jvm.Transient
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "users")
@Entity
@Getter
@Setter
open class User : DomaEntityImpl() {
  @OriginalStates // 差分UPDATEのために定義する
  @JsonIgnore
  var originalStates: // APIのレスポンスに含めない
    User? =
    null

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // ハッシュ化されたパスワード
  @JsonIgnore var password: String? = null

  // 名
  var firstName: String? = null

  // 姓
  var lastName: String? = null

  // メールアドレス
  var email: @Email String? = null

  // 電話番号
  var tel: @Digits(fraction = 0, integer = 10) String? = null

  // 郵便番号
  var zip: @NotEmpty String? = null

  // 住所
  var address: @NotEmpty String? = null

  // 画像ファイルID
  @JsonIgnore var uploadFileId: Long? = null

  // 画像ファイル
  @Transient // Domaで永続化しない
  @JsonIgnore
  var uploadFile: UploadFile? = null
}
