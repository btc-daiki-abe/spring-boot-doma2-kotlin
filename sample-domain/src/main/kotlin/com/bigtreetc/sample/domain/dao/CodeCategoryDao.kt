package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.CodeCategory
import com.bigtreetc.sample.domain.entity.CodeCategoryCriteria
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
interface CodeCategoryDao {
  /**
   * コード分類マスタを取得します。
   *
   * @param criteria
   * @param options
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: CodeCategoryCriteria,
    options: SelectOptions,
    collector: Collector<CodeCategory, *, R>,
  ): R

  /**
   * コード分類マスタを検索します。
   *
   * @param criteria
   * @return
   */
  @Select
  @Suppress(messages = [Message.DOMA4274])
  fun selectAll(criteria: CodeCategoryCriteria): Stream<CodeCategory>

  /**
   * コード分類マスタを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<CodeCategory>

  /**
   * コード分類マスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: CodeCategoryCriteria): Result<CodeCategory>

  /**
   * コード分類マスタを登録します。
   *
   * @param CodeCategory
   * @return
   */
  @Insert fun insert(CodeCategory: CodeCategory): Int

  /**
   * コード分類マスタを更新します。
   *
   * @param codeCategory
   * @return
   */
  @Update fun update(codeCategory: CodeCategory): Int

  /**
   * コード分類マスタを論理削除します。
   *
   * @param codeCategory
   * @return
   */
  @Update(excludeNull = true) fun delete(codeCategory: CodeCategory): Int

  /**
   * コード分類マスタを一括登録します。
   *
   * @param codes
   * @return
   */
  @BatchInsert fun insert(codes: List<CodeCategory>): IntArray
}
