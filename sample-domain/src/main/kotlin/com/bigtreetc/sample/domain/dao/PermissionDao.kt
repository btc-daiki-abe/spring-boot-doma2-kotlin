package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.Permission
import com.bigtreetc.sample.domain.entity.PermissionCriteria
import java.util.*
import java.util.stream.Collector
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.SelectOptions

@ConfigAutowireable
@Dao
interface PermissionDao {
  /**
   * 権限を取得します。
   *
   * @param criteria
   * @param options
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: PermissionCriteria,
    options: SelectOptions,
    collector: Collector<Permission, *, R>,
  ): R

  /**
   * 権限を1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<Permission>

  /**
   * 権限を1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: PermissionCriteria): Result<Permission>

  /**
   * 権限を登録します。
   *
   * @param permission
   * @return
   */
  @Insert fun insert(permission: Permission): Int

  /**
   * 権限を更新します。
   *
   * @param permission
   * @return
   */
  @Update fun update(permission: Permission): Int

  /**
   * 権限を論理削除します。
   *
   * @param permission
   * @return
   */
  @Update(excludeNull = true) fun delete(permission: Permission): Int

  /**
   * 権限を一括登録します。
   *
   * @param permissions
   * @return
   */
  @BatchInsert fun insert(permissions: List<Permission>): IntArray

  /**
   * 権限を一括更新します。
   *
   * @param permissions
   * @return
   */
  @BatchUpdate fun update(permissions: List<Permission>): IntArray
}
