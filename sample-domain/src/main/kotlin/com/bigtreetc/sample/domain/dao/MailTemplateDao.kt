package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.MailTemplate
import com.bigtreetc.sample.domain.entity.MailTemplateCriteria
import java.util.*
import java.util.stream.Collector
import java.util.stream.Stream
import org.seasar.doma.*
import org.seasar.doma.Suppress
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.SelectOptions
import org.seasar.doma.message.Message

@ConfigAutowireable
@Dao
interface MailTemplateDao {
  /**
   * メールテンプレートを検索します。
   *
   * @param criteria
   * @param options
   * @param collector
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: MailTemplateCriteria,
    options: SelectOptions,
    collector: Collector<MailTemplate, *, R>,
  ): R

  /**
   * メールテンプレートを検索します。
   *
   * @param criteria
   * @return
   */
  @Select
  @Suppress(messages = [Message.DOMA4274])
  fun selectAll(criteria: MailTemplateCriteria): Stream<MailTemplate>

  /**
   * メールテンプレートを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<MailTemplate>

  /**
   * メールテンプレートを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: MailTemplateCriteria): Result<MailTemplate>

  /**
   * メールテンプレートを登録します。
   *
   * @param mailTemplate
   * @return
   */
  @Insert fun insert(mailTemplate: MailTemplate): Int

  /**
   * メールテンプレートを更新します。
   *
   * @param mailTemplate
   * @return
   */
  @Update fun update(mailTemplate: MailTemplate): Int

  /**
   * メールテンプレートを論理削除します。
   *
   * @param mailTemplate
   * @return
   */
  @Update(excludeNull = true) fun delete(mailTemplate: MailTemplate): Int

  /**
   * メールテンプレートを一括登録します。
   *
   * @param mailTemplates
   * @return
   */
  @BatchInsert fun insert(mailTemplates: List<MailTemplate>): IntArray

  /**
   * メールテンプレートを一括更新します。
   *
   * @param mailTemplates
   * @return
   */
  @BatchUpdate fun update(mailTemplates: List<MailTemplate>): IntArray
}
