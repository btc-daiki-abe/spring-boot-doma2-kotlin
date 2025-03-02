package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.domain.dao.PermissionDao
import com.bigtreetc.sample.domain.entity.Permission
import com.bigtreetc.sample.domain.entity.PermissionCriteria
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.util.DomaUtils
import java.util.*
import java.util.stream.Collectors
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/** 権限リポジトリ */
@RequiredArgsConstructor
@Repository
class PermissionRepository(private val permissionDao: PermissionDao) {

  /**
   * 権限を検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: PermissionCriteria, pageable: Pageable): Page<Permission> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = permissionDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * 権限を取得します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: PermissionCriteria): Result<Permission> {
    return permissionDao.select(criteria)
  }

  /**
   * 権限を取得します。
   *
   * @return
   */
  fun findById(id: Long): Permission {
    return permissionDao.selectById(id).getOrElse {
      throw NoDataFoundException("permission_id=$id のデータが見つかりません。")
    }
  }

  /**
   * 権限を追加します。
   *
   * @param inputPermission
   * @return
   */
  fun create(inputPermission: Permission): Permission {
    permissionDao.insert(inputPermission)
    return inputPermission
  }

  /**
   * 権限を更新します。
   *
   * @param inputPermission
   * @return
   */
  fun update(inputPermission: Permission): Permission {
    val updated = permissionDao.update(inputPermission)

    if (updated < 1) {
      throw NoDataFoundException("permission_id=" + inputPermission.id + " のデータが見つかりません。")
    }

    return inputPermission
  }

  /**
   * 権限を論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Permission {
    val permission =
      permissionDao.selectById(id).getOrElse {
        throw NoDataFoundException("permission_id=$id のデータが見つかりません。")
      }

    val updated = permissionDao.delete(permission)

    if (updated < 1) {
      throw NoDataFoundException("permission_id=$id は更新できませんでした。")
    }

    return permission
  }
}
