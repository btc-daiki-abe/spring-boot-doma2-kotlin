package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "mail_templates")
@Entity
@Getter
@Setter
open class MailTemplate : DomaEntityImpl() {
  @OriginalStates
  var originalStates: // 差分UPDATEのために定義する
    MailTemplate? =
    null

  @Id
  @Column(name = "mail_template_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // カテゴリコード
  var categoryCode: String? = null

  // メールテンプレートコード
  var templateCode: String? = null

  // メールタイトル
  var subject: String? = null

  // メール本文
  var templateBody: String? = null
}
