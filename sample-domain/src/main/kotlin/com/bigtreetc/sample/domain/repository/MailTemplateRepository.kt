package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.domain.dao.MailTemplateDao
import com.bigtreetc.sample.domain.entity.MailTemplate
import com.bigtreetc.sample.domain.entity.MailTemplateCriteria
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.util.DomaUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/** メールテンプレートリポジトリ */
@RequiredArgsConstructor
@Repository
class MailTemplateRepository(private val mailTemplateDao: MailTemplateDao) {

  /**
   * メールテンプレートを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: MailTemplateCriteria, pageable: Pageable): Page<MailTemplate> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = mailTemplateDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * メールテンプレートを検索します。
   *
   * @return
   */
  fun findAll(criteria: MailTemplateCriteria): Stream<MailTemplate> {
    return mailTemplateDao.selectAll(criteria)
  }

  /**
   * メールテンプレートを1件取得します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: MailTemplateCriteria): Result<MailTemplate> {
    return mailTemplateDao.select(criteria)
  }

  /**
   * メールテンプレートを1件取得します。
   *
   * @return
   */
  fun findById(id: Long): MailTemplate {
    return mailTemplateDao.selectById(id).getOrElse {
      throw NoDataFoundException("mailTemplate_id=$id のデータが見つかりません。")
    }
  }

  /**
   * メールテンプレートを登録します。
   *
   * @param inputMailTemplate
   * @return
   */
  fun create(inputMailTemplate: MailTemplate): MailTemplate {
    mailTemplateDao.insert(inputMailTemplate)
    return inputMailTemplate
  }

  /**
   * メールテンプレートを更新します。
   *
   * @param inputMailTemplate
   * @return
   */
  fun update(inputMailTemplate: MailTemplate): MailTemplate {
    val updated = mailTemplateDao.update(inputMailTemplate)

    if (updated < 1) {
      throw NoDataFoundException("mailTemplate_id=" + inputMailTemplate.id + " のデータが見つかりません。")
    }

    return inputMailTemplate
  }

  /**
   * メールテンプレートを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): MailTemplate {
    val mailTemplate =
      mailTemplateDao.selectById(id).getOrElse {
        throw NoDataFoundException("mailTemplate_id=$id のデータが見つかりません。")
      }

    val updated = mailTemplateDao.delete(mailTemplate)

    if (updated < 1) {
      throw NoDataFoundException("mailTemplate_id=$id は更新できませんでした。")
    }

    return mailTemplate
  }
}
