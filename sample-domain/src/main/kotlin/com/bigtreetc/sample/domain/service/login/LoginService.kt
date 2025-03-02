package com.bigtreetc.sample.domain.service.login

import com.bigtreetc.sample.common.util.ValidateUtils.isEmpty
import com.bigtreetc.sample.domain.entity.MailTemplate
import com.bigtreetc.sample.domain.entity.MailTemplateCriteria
import com.bigtreetc.sample.domain.entity.Staff
import com.bigtreetc.sample.domain.entity.StaffCriteria
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.helper.SendMailHelper
import com.bigtreetc.sample.domain.repository.MailTemplateRepository
import com.bigtreetc.sample.domain.repository.StaffRepository
import com.bigtreetc.sample.domain.service.BaseTransactionalService
import java.time.LocalDateTime
import java.util.*
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.Assert

/** ログインサービス */
@RequiredArgsConstructor
@Service
class LoginService(
  @Value("\${spring.mail.properties.mail.from}") var fromAddress: String,
  private val staffRepository: StaffRepository,
  private val mailTemplateRepository: MailTemplateRepository,
  private val sendMailHelper: SendMailHelper,
) : BaseTransactionalService() {

  /**
   * パスワードリセットメールを送信します。
   *
   * @param email
   * @param url
   */
  fun sendResetPasswordMail(email: String, url: String) {
    Assert.notNull(fromAddress, "fromAddress must be set.")

    val criteria: StaffCriteria = StaffCriteria()
    criteria.email = email
    val staff = staffRepository.findOne(criteria)

    staff.onSuccess { s: Staff ->
      // トークンを発行する
      val token = UUID.randomUUID().toString()
      s.passwordResetToken = token
      s.tokenExpiresAt = LocalDateTime.now().plusHours(2) // 2時間後に失効させる
      staffRepository.update(s)

      // メールを作成する
      val mailTemplate = getMailTemplate("passwordReset")
      val subject = mailTemplate.subject
      val templateBody = mailTemplate.templateBody

      val objects: MutableMap<String, Any> = HashMap()
      objects["staff"] = s
      objects["url"] = "$url?token=$token"

      // テンプレートエンジンにかける
      val body = sendMailHelper.getMailBody(templateBody, objects)

      // メールを送信する
      sendMailHelper.sendMail(fromAddress, s.email, subject, body)
    }
  }

  /**
   * トークンの有効性をチェックします。
   *
   * @param token
   * @return
   */
  fun isValidPasswordResetToken(token: String): Boolean {
    if (isEmpty(token)) {
      return false
    }

    // トークンの一致と有効期限をチェックする
    val criteria: StaffCriteria = StaffCriteria()
    criteria.passwordResetToken = token
    val staff = staffRepository.findOne(criteria)

    staff.onFailure {
      return false
    }

    return true
  }

  /**
   * パスワードを更新します。
   *
   * @param token
   * @param password
   * @return
   */
  fun updatePassword(token: String, password: String): Boolean {
    // トークンの一致と有効期限をチェックする
    val criteria: StaffCriteria = StaffCriteria()
    criteria.passwordResetToken = token
    val staff = staffRepository.findOne(criteria)

    staff.onFailure {
      return false
    }

    staff.onSuccess { s: Staff ->
      // パスワードをリセットする
      s.passwordResetToken = null
      s.tokenExpiresAt = null
      s.password = password
      staffRepository.update(s)
    }

    return true
  }

  /**
   * メールテンプレートを取得する。
   *
   * @return
   */
  protected fun getMailTemplate(templateCode: String): MailTemplate {
    val criteria: MailTemplateCriteria = MailTemplateCriteria()
    criteria.templateCode = templateCode
    return mailTemplateRepository.findOne(criteria).getOrElse {
      throw NoDataFoundException("templateCode=" + criteria.templateCode + " のデータが見つかりません。")
    }
  }
}
