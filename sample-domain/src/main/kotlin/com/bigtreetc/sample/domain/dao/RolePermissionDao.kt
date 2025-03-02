package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.RolePermission
import com.bigtreetc.sample.domain.entity.RolePermissionCriteria
import java.util.*
import java.util.stream.Collector
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.SelectOptions

@ConfigAutowireable
@Dao
interface RolePermissionDao {
  /**
   * ロール権限紐付けを取得します。
   *
   * @param criteria
   * @param options
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    criteria: RolePermissionCriteria,
    options: SelectOptions,
    collector: Collector<RolePermission, *, R>,
  ): R

  /**
   * ロール権限紐付けを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<RolePermission>

  /**
   * ロール権限紐付けを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: RolePermissionCriteria): Result<RolePermission>

  /**
   * ロール権限紐付けを登録します。
   *
   * @param rolePermission
   * @return
   */
  @Insert fun insert(rolePermission: RolePermission): Int

  /**
   * ロール権限紐付けを更新します。
   *
   * @param rolePermission
   * @return
   */
  @Update fun update(rolePermission: RolePermission): Int

  /**
   * ロール権限紐付けを論理削除します。
   *
   * @param rolePermission
   * @return
   */
  @Update(excludeNull = true) fun delete(rolePermission: RolePermission): Int

  /**
   * ロール権限紐付けを一括論理削除します。
   *
   * @param rolePermissions
   * @return
   */
  @BatchUpdate fun delete(rolePermissions: List<RolePermission>): IntArray

  /**
   * ロール権限紐付けを一括登録します。
   *
   * @param rolePermissions
   * @return
   */
  @BatchInsert fun insert(rolePermissions: List<RolePermission>): IntArray
}
