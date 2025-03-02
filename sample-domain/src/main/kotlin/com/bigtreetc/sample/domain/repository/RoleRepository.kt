package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.common.util.ValidateUtils.isNotEmpty
import com.bigtreetc.sample.domain.dao.RoleDao
import com.bigtreetc.sample.domain.dao.RolePermissionDao
import com.bigtreetc.sample.domain.entity.*
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.util.DomaUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/** ロールマスタリポジトリ */
@RequiredArgsConstructor
@Repository
class RoleRepository(
  private val roleDao: RoleDao,
  private val rolePermissionDao: RolePermissionDao,
) {

  /**
   * ロールマスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: RoleCriteria, pageable: Pageable): Page<Role> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = roleDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * ロールマスタを検索します。
   *
   * @return
   */
  fun findAll(criteria: RoleCriteria): Stream<Role> {
    return roleDao.selectAll(criteria)
  }

  /**
   * ロールマスタを検索します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: RoleCriteria): Result<Role> {
    val role = roleDao.select(criteria)

    role.onSuccess { r: Role ->
      val rolePermissions = findRolePermissions(r)
      if (isNotEmpty(rolePermissions)) {
        r.rolePermissions.toMutableList().addAll(rolePermissions)
      }
    }

    return role
  }

  /**
   * ロールを取得します。
   *
   * @return
   */
  fun findById(id: Long): Role {
    val role =
      roleDao.selectById(id).getOrElse { throw NoDataFoundException("role_id=$id のデータが見つかりません。") }

    val rolePermissions = findRolePermissions(role)
    if (isNotEmpty(rolePermissions)) {
      role.rolePermissions.toMutableList().addAll(rolePermissions)
    }

    return role
  }

  /**
   * ロールを追加します。
   *
   * @param inputRole
   * @return
   */
  fun create(inputRole: Role): Role {
    roleDao.insert(inputRole)

    // ロール権限紐付けを登録する
    val rolePermissions: List<RolePermission> = inputRole.rolePermissions
    rolePermissionDao.insert(rolePermissions)

    return inputRole
  }

  /**
   * ロールを更新します。
   *
   * @param inputRole
   * @return
   */
  fun update(inputRole: Role): Role {
    val updated = roleDao.update(inputRole)

    if (updated < 1) {
      throw NoDataFoundException("role_id=" + inputRole.id + " のデータが見つかりません。")
    }

    // ロール権限紐付けを更新する
    val roleCode = inputRole.roleCode
    val rolePermissions = inputRole.rolePermissions
    for (rp in rolePermissions) {
      rp.roleCode = roleCode
      rolePermissionDao.update(rp)
    }

    return inputRole
  }

  /**
   * ロールを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Role {
    val role =
      roleDao.selectById(id).getOrElse { throw NoDataFoundException("role_id=$id のデータが見つかりません。") }

    val updated = roleDao.delete(role)

    if (updated < 1) {
      throw NoDataFoundException("role_id=$id は更新できませんでした。")
    }

    // ロール権限紐付けを論理削除する
    deleteRolePermissions(role)

    return role
  }

  /**
   * ロール権限紐付けを論理削除する
   *
   * @param inputRole
   */
  protected fun deleteRolePermissions(inputRole: Role) {
    val rolePermissionsToDelete = findRolePermissions(inputRole)

    if (isNotEmpty(rolePermissionsToDelete)) {
      rolePermissionDao.delete(rolePermissionsToDelete) // 一括論理削除
    }
  }

  /**
   * ロール権限紐付けを取得する
   *
   * @param inputRole
   * @return
   */
  protected fun findRolePermissions(inputRole: Role): MutableList<RolePermission> {
    // ロール権限紐付けをロールコードで取得する
    val criteria = RolePermissionCriteria()
    criteria.roleCode = inputRole.roleCode

    val options = DomaUtils.createSelectOptions(Pageable.unpaged())
    return rolePermissionDao.selectAll(criteria, options, Collectors.toList())
  }
}
