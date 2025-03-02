package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Email
import java.time.LocalDateTime
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "staffs")
@Entity
@Getter
@Setter
open class Staff : DomaEntityImpl() {
  @OriginalStates
  var originalStates: // 差分UPDATEのために定義する
    Staff? =
    null

  @Id
  @Column(name = "staff_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  var password: String? = null

  // 名
  var firstName: String? = null

  // 姓
  var lastName: String? = null

  // メールアドレス
  var email: @Email String? = null

  // 電話番号
  var tel: @Digits(fraction = 0, integer = 10) String? = null

  // パスワードリセットトークン
  var passwordResetToken: String? = null

  // トークン失効日
  var tokenExpiresAt: LocalDateTime? = null
}
