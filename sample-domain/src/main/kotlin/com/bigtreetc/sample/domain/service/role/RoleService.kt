package com.bigtreetc.sample.domain.service.role

import com.bigtreetc.sample.domain.entity.*
import com.bigtreetc.sample.domain.repository.PermissionRepository
import com.bigtreetc.sample.domain.repository.RoleRepository
import com.bigtreetc.sample.domain.service.BaseTransactionalService
import com.bigtreetc.sample.domain.util.CsvUtils
import java.io.IOException
import java.io.OutputStream
import java.util.*
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

/** ロールマスタサービス */
@RequiredArgsConstructor
@Service
class RoleService(
  private val roleRepository: RoleRepository,
  private val permissionRepository: PermissionRepository,
) : BaseTransactionalService() {

  /**
   * ロールマスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: RoleCriteria, pageable: Pageable): Page<Role> {
    Assert.notNull(criteria, "criteria must not be null")
    return roleRepository.findAll(criteria, pageable)
  }

  /**
   * ロールマスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: RoleCriteria): Result<Role> {
    Assert.notNull(criteria, "criteria must not be null")
    val role = roleRepository.findOne(criteria)

    role.onSuccess { r: Role -> r.permissions.toMutableList().addAll(permissions) }

    return role
  }

  /**
   * ロールマスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): Role {
    Assert.notNull(id, "id must not be null")
    val role = roleRepository.findById(id)
    role.permissions.toMutableList().addAll(permissions)
    return role
  }

  /**
   * ロールマスタを追加します。
   *
   * @param inputRole
   * @return
   */
  fun create(inputRole: Role): Role {
    Assert.notNull(inputRole, "inputRole must not be null")
    return roleRepository.create(inputRole)
  }

  /**
   * ロールマスタを更新します。
   *
   * @param inputRole
   * @return
   */
  fun update(inputRole: Role): Role {
    Assert.notNull(inputRole, "inputRole must not be null")
    return roleRepository.update(inputRole)
  }

  /**
   * ロールマスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Role {
    Assert.notNull(id, "id must not be null")
    return roleRepository.delete(id)
  }

  /**
   * ロールマスタを書き出します。
   *
   * @param outputStream
   * @param
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  @Throws(IOException::class)
  fun writeToOutputStream(outputStream: OutputStream, criteria: RoleCriteria, clazz: Class<*>) {
    Assert.notNull(criteria, "criteria must not be null")
    roleRepository.findAll(criteria).use { data ->
      CsvUtils.writeCsv(outputStream, clazz, data) { role: Any? -> modelMapper.map(role, clazz) }
    }
  }

  private val permissions: List<Permission>
    get() = permissionRepository.findAll(PermissionCriteria(), Pageable.unpaged()).content
}
