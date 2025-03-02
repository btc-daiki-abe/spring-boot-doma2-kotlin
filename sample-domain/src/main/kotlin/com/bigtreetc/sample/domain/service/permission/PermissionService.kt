package com.bigtreetc.sample.domain.service.permission

import com.bigtreetc.sample.domain.entity.Permission
import com.bigtreetc.sample.domain.entity.PermissionCriteria
import com.bigtreetc.sample.domain.repository.PermissionRepository
import com.bigtreetc.sample.domain.service.BaseTransactionalService
import java.util.*
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

/** 権限サービス */
@RequiredArgsConstructor
@Service
class PermissionService(private val permissionRepository: PermissionRepository) :
  BaseTransactionalService() {

  /**
   * 権限を検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: PermissionCriteria, pageable: Pageable): Page<Permission> {
    Assert.notNull(criteria, "criteria must not be null")
    return permissionRepository.findAll(criteria, pageable)
  }

  /**
   * 権限を取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: PermissionCriteria): Result<Permission> {
    Assert.notNull(criteria, "criteria must not be null")
    return permissionRepository.findOne(criteria)
  }

  /**
   * 権限を取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): Permission {
    Assert.notNull(id, "id must not be null")
    return permissionRepository.findById(id)
  }

  /**
   * 権限を追加します。
   *
   * @param inputPermission
   * @return
   */
  fun create(inputPermission: Permission): Permission {
    Assert.notNull(inputPermission, "inputPermission must not be null")
    return permissionRepository.create(inputPermission)
  }

  /**
   * 権限を更新します。
   *
   * @param inputPermission
   * @return
   */
  fun update(inputPermission: Permission): Permission {
    Assert.notNull(inputPermission, "inputPermission must not be null")
    return permissionRepository.update(inputPermission)
  }

  /**
   * 権限を論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Permission {
    Assert.notNull(id, "id must not be null")
    return permissionRepository.delete(id)
  }
}
