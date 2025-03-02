package com.bigtreetc.sample.domain.service.staff

import com.bigtreetc.sample.domain.entity.Staff
import com.bigtreetc.sample.domain.entity.StaffCriteria
import com.bigtreetc.sample.domain.repository.StaffRepository
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

/** 担当者マスタサービス */
@RequiredArgsConstructor
@Service
class StaffService(private val staffRepository: StaffRepository) : BaseTransactionalService() {

  /**
   * 担当者マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: StaffCriteria, pageable: Pageable): Page<Staff> {
    Assert.notNull(criteria, "criteria must not be null")
    return staffRepository.findAll(criteria, pageable)
  }

  /**
   * 担当者マスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: StaffCriteria): Result<Staff> {
    Assert.notNull(criteria, "criteria must not be null")
    return staffRepository.findOne(criteria)
  }

  /**
   * 担当者マスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): Staff {
    Assert.notNull(id, "id must not be null")
    return staffRepository.findById(id)
  }

  /**
   * 担当者マスタを追加します。
   *
   * @param inputStaff
   * @return
   */
  fun create(inputStaff: Staff): Staff {
    Assert.notNull(inputStaff, "inputStaff must not be null")
    return staffRepository.create(inputStaff)
  }

  /**
   * 担当者マスタを更新します。
   *
   * @param inputStaff
   * @return
   */
  fun update(inputStaff: Staff): Staff {
    Assert.notNull(inputStaff, "inputStaff must not be null")
    return staffRepository.update(inputStaff)
  }

  /**
   * 担当者マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Staff {
    Assert.notNull(id, "id must not be null")
    return staffRepository.delete(id)
  }

  /**
   * 担当者マスタを書き出します。
   *
   * @param outputStream
   * @param
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  @Throws(IOException::class)
  fun writeToOutputStream(outputStream: OutputStream, criteria: StaffCriteria, clazz: Class<*>) {
    Assert.notNull(criteria, "criteria must not be null")
    staffRepository.findAll(criteria).use { data ->
      CsvUtils.writeCsv(outputStream, clazz, data) { staff: Any? -> modelMapper.map(staff, clazz) }
    }
  }
}
