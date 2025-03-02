package com.bigtreetc.sample.domain.helper

import com.bigtreetc.sample.common.util.ValidateUtils.isNotEmpty
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templateresolver.ITemplateResolver
import org.thymeleaf.templateresolver.StringTemplateResolver

/** メール送信ヘルパー */
@Component
class SendMailHelper {

  @Autowired var javaMailSender: JavaMailSender? = null

  private val log = LoggerFactory.getLogger(javaClass)

  /**
   * メールを送信します。
   *
   * @param fromAddress
   * @param toAddress
   * @param subject
   * @param body
   */
  fun sendMail(fromAddress: String?, toAddress: String?, subject: String?, body: String?) {
    this.sendMail(fromAddress, arrayOf(toAddress), null, null, subject, body)
  }

  /**
   * メールを送信します。
   *
   * @param fromAddress
   * @param toAddress
   * @param tempCcAddress
   * @param tempBccAddress
   * @param subject
   * @param body
   */
  fun sendMail(
    fromAddress: String?,
    toAddress: String?,
    tempCcAddress: String?,
    tempBccAddress: String?,
    subject: String?,
    body: String?,
  ) {
    val ccAddress = getCommaSeparatedStrings(tempCcAddress)
    val bccAddress = getCommaSeparatedStrings(tempBccAddress)
    this.sendMail(fromAddress, arrayOf(toAddress), ccAddress, bccAddress, subject, body)
  }

  /**
   * メールを送信します。
   *
   * @param fromAddress
   * @param toAddress
   * @param ccAddress
   * @param bccAddress
   * @param subject
   * @param body
   */
  fun sendMail(
    fromAddress: String?,
    toAddress: Array<String?>,
    ccAddress: Array<String>?,
    bccAddress: Array<String>?,
    subject: String?,
    body: String?,
  ) {
    val message = SimpleMailMessage()
    message.from = fromAddress
    message.setTo(*toAddress)
    if (ccAddress != null && ccAddress.size > 0) {
      message.setCc(*ccAddress)
    }
    if (bccAddress != null && bccAddress.size > 0) {
      message.setBcc(*bccAddress)
    }
    message.subject = subject
    message.text = body

    try {
      javaMailSender!!.send(message)
    } catch (e: MailException) {
      log.error("failed toAddress send mail.", e)
      throw e
    }
  }

  /**
   * 指定したテンプレートのメール本文を返します。
   *
   * @param template
   * @param objects
   * @return
   */
  fun getMailBody(template: String?, objects: Map<String, Any?>): String {
    val templateEngine = SpringTemplateEngine()
    templateEngine.setTemplateResolver(templateResolver())

    val context = Context()
    if (isNotEmpty(objects)) {
      objects.forEach { (name: String?, value: Any?) -> context.setVariable(name, value) }
    }

    return templateEngine.process(template, context)
  }

  protected fun templateResolver(): ITemplateResolver {
    val resolver = StringTemplateResolver()
    resolver.setTemplateMode("TEXT")
    resolver.isCacheable = false // 安全をとってキャッシュしない
    return resolver
  }

  private fun getCommaSeparatedStrings(str: String?): Array<String>? {
    if (Objects.nonNull(str)) {
      return str!!.split(",".toRegex()).stream().map { it.trim() }.toList().toTypedArray()
    }
    return null
  }
}
