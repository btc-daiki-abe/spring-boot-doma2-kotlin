package com.bigtreetc.sample.domain.service.mailtemplate

import com.bigtreetc.sample.domain.entity.MailTemplate
import com.bigtreetc.sample.domain.entity.MailTemplateCriteria
import com.bigtreetc.sample.domain.repository.MailTemplateRepository
import com.bigtreetc.sample.domain.service.BaseTransactionalService
import com.bigtreetc.sample.domain.util.CsvUtils
import java.io.IOException
import java.io.OutputStream
import java.util.*
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

/** メールテンプレートサービス */
@RequiredArgsConstructor
@Service
class MailTemplateService(private val mailTemplateRepository: MailTemplateRepository) :
  BaseTransactionalService() {

  /**
   * メールテンプレートを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: MailTemplateCriteria, pageable: Pageable): Page<MailTemplate> {
    Assert.notNull(criteria, "criteria must not be null")
    return mailTemplateRepository.findAll(criteria, pageable)
  }

  /**
   * メールテンプレートを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: MailTemplateCriteria): Result<MailTemplate> {
    Assert.notNull(criteria, "criteria must not be null")
    return mailTemplateRepository.findOne(criteria)
  }

  /**
   * メールテンプレートを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): MailTemplate {
    Assert.notNull(id, "id must not be null")
    return mailTemplateRepository.findById(id)
  }

  /**
   * メールテンプレートを追加します。
   *
   * @param inputMailTemplate
   * @return
   */
  fun create(inputMailTemplate: MailTemplate): MailTemplate {
    Assert.notNull(inputMailTemplate, "inputMailTemplate must not be null")
    return mailTemplateRepository.create(inputMailTemplate)
  }

  /**
   * メールテンプレートを更新します。
   *
   * @param inputMailTemplate
   * @return
   */
  fun update(inputMailTemplate: MailTemplate): MailTemplate {
    Assert.notNull(inputMailTemplate, "inputMailTemplate must not be null")
    return mailTemplateRepository.update(inputMailTemplate)
  }

  /**
   * メールテンプレートを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): MailTemplate {
    Assert.notNull(id, "id must not be null")
    return mailTemplateRepository.delete(id)
  }

  /**
   * メールテンプレートを書き出します。
   *
   * @param outputStream
   * @param
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  @Throws(IOException::class)
  fun writeToOutputStream(
    outputStream: OutputStream,
    criteria: MailTemplateCriteria,
    clazz: Class<*>,
  ) {
    Assert.notNull(criteria, "criteria must not be null")
    mailTemplateRepository.findAll(criteria).use { data ->
      CsvUtils.writeCsv(outputStream, clazz, data) { mailTemplate: Any? ->
        modelMapper.map(mailTemplate, clazz)
      }
    }
  }
}
