package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.*
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
interface RoleDao {
  /**
   * ロールマスタを検索します。
   *
   * @param criteria
   * @param options
   * @param collector
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: RoleCriteria,
    options: SelectOptions,
    collector: Collector<Role, *, R>,
  ): R

  /**
   * ロールマスタを検索します。
   *
   * @param criteria
   * @return
   */
  @Select
  @Suppress(messages = [Message.DOMA4274])
  fun selectAll(criteria: RoleCriteria): Stream<Role>

  /**
   * ロールマスタを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<Role>

  /**
   * ロールマスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: RoleCriteria): Result<Role>

  /**
   * ロールマスタを登録します。
   *
   * @param role
   * @return
   */
  @Insert fun insert(role: Role): Int

  /**
   * ロールマスタを更新します。
   *
   * @param role
   * @return
   */
  @Update fun update(role: Role): Int

  /**
   * ロールマスタを論理削除します。
   *
   * @param role
   * @return
   */
  @Update(excludeNull = true) fun delete(role: Role): Int

  /**
   * ロールマスタを一括登録します。
   *
   * @param roles
   * @return
   */
  @BatchInsert fun insert(roles: List<Role>): IntArray

  /**
   * ロールマスタを一括更新します。
   *
   * @param roles
   * @return
   */
  @BatchUpdate fun update(roles: List<Role>): IntArray
}
