package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.*
import java.util.*
import java.util.stream.Collector
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.SelectOptions

@ConfigAutowireable
@Dao
interface UserRoleDao {
  /**
   * 権限を取得します。
   *
   * @param userCriteria
   * @param permissionCriteria
   * @param options
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    userCriteria: UserCriteria,
    permissionCriteria: PermissionCriteria,
    options: SelectOptions,
    collector: Collector<UserRole, *, R>,
  ): R

  /**
   * 権限を取得します。
   *
   * @param id
   * @param collector
   * @param <R>
   * @return </R>
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectByUserId(id: Long, collector: Collector<UserRole, *, R>): R

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
   * @param userRole
   * @return
   */
  @Insert(exclude = ["roleName", "permissionCode", "permissionName"])
  fun insert(userRole: UserRole): Int

  /**
   * 権限を更新します。
   *
   * @param userRole
   * @return
   */
  @Update(exclude = ["roleName", "permissionCode", "permissionName"])
  fun update(userRole: UserRole): Int

  /**
   * 権限を論理削除します。
   *
   * @param userRole
   * @return
   */
  @Update(excludeNull = true) fun delete(userRole: UserRole): Int

  /**
   * 権限を一括登録します。
   *
   * @param userRoles
   * @return
   */
  @BatchInsert(exclude = ["roleName", "permissionCode", "permissionName"])
  fun insert(userRoles: List<UserRole>): IntArray
}
