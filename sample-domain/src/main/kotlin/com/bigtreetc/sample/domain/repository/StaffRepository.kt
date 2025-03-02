package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.domain.dao.RolePermissionDao
import com.bigtreetc.sample.domain.dao.StaffDao
import com.bigtreetc.sample.domain.dao.StaffRoleDao
import com.bigtreetc.sample.domain.entity.Staff
import com.bigtreetc.sample.domain.entity.StaffCriteria
import com.bigtreetc.sample.domain.entity.StaffRole
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

/** 担当者マスタリポジトリ */
@RequiredArgsConstructor
@Repository
class StaffRepository(
  private val staffDao: StaffDao,
  private val staffRoleDao: StaffRoleDao,
  private val rolePermissionDao: RolePermissionDao,
) {

  /**
   * 担当者マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: StaffCriteria, pageable: Pageable): Page<Staff> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = staffDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * 担当者マスタを検索します。
   *
   * @return
   */
  fun findAll(criteria: StaffCriteria): Stream<Staff> {
    return staffDao.selectAll(criteria)
  }

  /**
   * 担当者マスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: StaffCriteria): Result<Staff> {
    return staffDao.select(criteria)
  }

  /**
   * 担当者マスタを1件取得します。
   *
   * @return
   */
  fun findById(id: Long): Staff {
    return staffDao.selectById(id).getOrElse {
      throw NoDataFoundException("staff_id=$id のデータが見つかりません。")
    }
  }

  /**
   * 担当者マスタを登録します。
   *
   * @param inputStaff
   * @return
   */
  fun create(inputStaff: Staff): Staff {
    staffDao.insert(inputStaff)

    // ロール権限紐付けを登録する
    val staffRole = StaffRole()
    staffRole.staffId = inputStaff.id
    staffRole.roleCode = "admin"
    staffRoleDao.insert(staffRole)

    return inputStaff
  }

  /**
   * 担当者マスタを更新します。
   *
   * @param inputStaff
   * @return
   */
  fun update(inputStaff: Staff): Staff {
    val updated = staffDao.update(inputStaff)

    if (updated < 1) {
      throw NoDataFoundException("staff_id=" + inputStaff!!.id + " のデータが見つかりません。")
    }

    return inputStaff
  }

  /**
   * 担当者マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Staff {
    val staff =
      staffDao.selectById(id).getOrElse { throw NoDataFoundException("staff_id=$id のデータが見つかりません。") }

    val updated = staffDao.delete(staff)

    if (updated < 1) {
      throw NoDataFoundException("staff_id=$id は更新できませんでした。")
    }

    return staff
  }
}
