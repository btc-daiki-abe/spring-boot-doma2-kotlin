package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import java.time.LocalDateTime
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "send_mail_queue")
@Entity
@Getter
@Setter
open class SendMailQueue : DomaEntityImpl() {
  @OriginalStates
  var originalStates: // 差分UPDATEのために定義する
    SendMailQueue? =
    null

  @Id
  @Column(name = "send_mail_queue_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  @Column(name = "from_address") var from: String? = null

  @Column(name = "to_address") var to: String? = null

  @Column(name = "cc_address") var cc: String? = null

  @Column(name = "bcc_address") var bcc: String? = null

  var sentAt: LocalDateTime? = null

  var subject: String? = null

  var body: String? = null
}
