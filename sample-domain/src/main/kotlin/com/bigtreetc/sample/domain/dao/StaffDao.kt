package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.Staff
import com.bigtreetc.sample.domain.entity.StaffCriteria
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
interface StaffDao {
  /**
   * 担当者マスタを検索します。
   *
   * @param criteria
   * @param options
   * @param collector
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: StaffCriteria,
    options: SelectOptions,
    collector: Collector<Staff, *, R>,
  ): R

  /**
   * 担当者マスタを検索します。
   *
   * @param criteria
   * @return
   */
  @Select
  @Suppress(messages = [Message.DOMA4274])
  fun selectAll(criteria: StaffCriteria): Stream<Staff>

  /**
   * 担当者マスタを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<Staff>

  /**
   * 担当者マスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: StaffCriteria): Result<Staff>

  /**
   * 担当者マスタを登録します。
   *
   * @param staff
   * @return
   */
  @Insert fun insert(staff: Staff): Int

  /**
   * 担当者マスタを更新します。
   *
   * @param staff
   * @return
   */
  @Update fun update(staff: Staff): Int

  /**
   * 担当者マスタを論理削除します。
   *
   * @param staff
   * @return
   */
  @Update(excludeNull = true) fun delete(staff: Staff): Int

  /**
   * 担当者マスタを一括登録します。
   *
   * @param staffs
   * @return
   */
  @BatchInsert fun insert(staffs: List<Staff>): IntArray

  /**
   * 担当者マスタを一括更新します。
   *
   * @param staffs
   * @return
   */
  @BatchUpdate fun update(staffs: List<Staff>): IntArray
}
