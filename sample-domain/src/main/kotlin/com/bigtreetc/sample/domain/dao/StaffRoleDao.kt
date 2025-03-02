package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.*
import java.util.*
import java.util.stream.Collector
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.SelectOptions

@ConfigAutowireable
@Dao
interface StaffRoleDao {
  /**
   * 担当者権限を取得します。
   *
   * @param staffCriteria
   * @param permissionCriteria
   * @param options
   * @return
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectAll(
    staffCriteria: StaffCriteria,
    permissionCriteria: PermissionCriteria,
    options: SelectOptions,
    collector: Collector<StaffRole, *, R>,
  ): R

  /**
   * 担当者権限を取得します。
   *
   * @param id
   * @param collector
   * @param <R>
   * @return </R>
   */
  @Select(strategy = SelectType.COLLECT)
  fun <R> selectByStaffId(id: Long, collector: Collector<StaffRole, *, R>): R

  /**
   * 担当者権限を1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): Result<Permission>

  /**
   * 担当者権限を1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: PermissionCriteria): Result<Permission>

  /**
   * 担当者権限を登録します。
   *
   * @param staffRole
   * @return
   */
  @Insert(exclude = ["roleName", "permissionCode", "permissionName"])
  fun insert(staffRole: StaffRole): Int

  /**
   * 担当者権限を更新します。
   *
   * @param staffRole
   * @return
   */
  @Update(exclude = ["roleName", "permissionCode", "permissionName"])
  fun update(staffRole: StaffRole): Int

  /**
   * 担当者権限を論理削除します。
   *
   * @param staffRole
   * @return
   */
  @Update(excludeNull = true) fun delete(staffRole: StaffRole): Int

  /**
   * 担当者権限を一括登録します。
   *
   * @param staffRoles
   * @return
   */
  @BatchInsert(exclude = ["roleName", "permissionCode", "permissionName"])
  fun insert(staffRoles: List<StaffRole>): IntArray
}
