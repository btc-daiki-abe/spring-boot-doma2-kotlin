package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.Code
import com.bigtreetc.sample.domain.entity.CodeCriteria
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
interface CodeDao {
  /**
   * コードマスタを検索します。
   *
   * @param criteria
   * @param options
   * @param collector
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: CodeCriteria,
    options: SelectOptions,
    collector: Collector<Code, *, R>,
  ): R

  /**
   * コードマスタを検索します。
   *
   * @param criteria
   * @return
   */
  @Select
  @Suppress(messages = [Message.DOMA4274])
  fun selectAll(criteria: CodeCriteria): Stream<Code>

  /**
   * コードマスタを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<Code>

  /**
   * コードマスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: CodeCriteria): Result<Code>

  /**
   * コードマスタを登録します。
   *
   * @param code
   * @return
   */
  @Insert fun insert(code: Code): Int

  /**
   * コードマスタを更新します。
   *
   * @param code
   * @return
   */
  @Update fun update(code: Code): Int

  /**
   * コードマスタを論理削除します。
   *
   * @param code
   * @return
   */
  @Update(excludeNull = true) fun delete(code: Code): Int

  /**
   * コードマスタを一括登録します。
   *
   * @param codes
   * @return
   */
  @BatchInsert fun insert(codes: List<Code>): IntArray

  /**
   * コードマスタを一括更新します。
   *
   * @param codes
   * @return
   */
  @BatchUpdate fun update(codes: List<Code>): IntArray
}
