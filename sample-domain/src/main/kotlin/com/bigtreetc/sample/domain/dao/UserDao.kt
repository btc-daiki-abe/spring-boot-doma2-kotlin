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
interface UserDao {
  /**
   * 顧客マスタを検索します。
   *
   * @param criteria
   * @param options
   * @param collector
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: UserCriteria,
    options: SelectOptions,
    collector: Collector<User, *, R>,
  ): R

  /**
   * 顧客マスタを検索します。
   *
   * @param criteria
   * @return
   */
  @Select
  @Suppress(messages = [Message.DOMA4274])
  fun selectAll(criteria: UserCriteria): Stream<User>

  /**
   * 顧客マスタを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<User>

  /**
   * 顧客マスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: UserCriteria): Result<User>

  /**
   * 顧客マスタを登録します。
   *
   * @param user
   * @return
   */
  @Insert fun insert(user: User): Int

  /**
   * 顧客マスタを更新します。
   *
   * @param user
   * @return
   */
  @Update fun update(user: User): Int

  /**
   * 顧客マスタを論理削除します。
   *
   * @param user
   * @return
   */
  @Update(excludeNull = true) fun delete(user: User): Int

  /**
   * 顧客マスタを一括登録します。
   *
   * @param users
   * @return
   */
  @BatchInsert fun insert(users: List<User>): IntArray

  /**
   * 顧客マスタを一括更新します。
   *
   * @param users
   * @return
   */
  @BatchUpdate fun update(users: List<User>): IntArray
}
