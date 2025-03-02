package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.Holiday
import com.bigtreetc.sample.domain.entity.HolidayCriteria
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
interface HolidayDao {
  /**
   * 祝日マスタを検索します。
   *
   * @param criteria
   * @param options
   * @param collector
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: HolidayCriteria,
    options: SelectOptions,
    collector: Collector<Holiday, *, R>,
  ): R

  /**
   * 祝日マスタを検索します。
   *
   * @param criteria
   * @return
   */
  @Select
  @Suppress(messages = [Message.DOMA4274])
  fun selectAll(criteria: HolidayCriteria): Stream<Holiday>

  /**
   * 祝日マスタを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<Holiday>

  /**
   * 祝日マスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: HolidayCriteria): Result<Holiday>

  /**
   * 祝日マスタを登録します。
   *
   * @param holiday
   * @return
   */
  @Insert fun insert(holiday: Holiday): Int

  /**
   * 祝日マスタを更新します。
   *
   * @param holiday
   * @return
   */
  @Update fun update(holiday: Holiday): Int

  /**
   * 祝日マスタを論理削除します。
   *
   * @param holiday
   * @return
   */
  @Update(excludeNull = true) fun delete(holiday: Holiday): Int

  /**
   * 祝日マスタを一括登録します。
   *
   * @param holidays
   * @return
   */
  @BatchInsert fun insert(holidays: List<Holiday>): IntArray

  /**
   * 祝日マスタを一括更新します。
   *
   * @param holidays
   * @return
   */
  @BatchUpdate fun update(holidays: List<Holiday>): IntArray
}
