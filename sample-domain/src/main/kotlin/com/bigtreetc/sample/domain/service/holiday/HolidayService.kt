package com.bigtreetc.sample.domain.service.holiday

import com.bigtreetc.sample.domain.entity.*
import com.bigtreetc.sample.domain.repository.HolidayRepository
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

/** 祝日マスタサービス */
@RequiredArgsConstructor
@Service
class HolidayService(private val holidayRepository: HolidayRepository) :
  BaseTransactionalService() {

  /**
   * 祝日マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: HolidayCriteria, pageable: Pageable): Page<Holiday> {
    Assert.notNull(criteria, "criteria must not be null")
    return holidayRepository.findAll(criteria, pageable)
  }

  /**
   * 祝日マスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: HolidayCriteria): Result<Holiday> {
    Assert.notNull(criteria, "criteria must not be null")
    return holidayRepository.findOne(criteria)
  }

  /**
   * 祝日マスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): Holiday {
    Assert.notNull(id, "id must not be null")
    return holidayRepository.findById(id)
  }

  /**
   * 祝日マスタを追加します。
   *
   * @param inputHoliday
   * @return
   */
  fun create(inputHoliday: Holiday): Holiday {
    Assert.notNull(inputHoliday, "inputHoliday must not be null")
    return holidayRepository.create(inputHoliday)
  }

  /**
   * 祝日マスタを更新します。
   *
   * @param inputHoliday
   * @return
   */
  fun update(inputHoliday: Holiday): Holiday {
    Assert.notNull(inputHoliday, "inputHoliday must not be null")
    return holidayRepository.update(inputHoliday)
  }

  /**
   * 祝日マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Holiday {
    Assert.notNull(id, "id must not be null")
    return holidayRepository.delete(id)
  }

  /**
   * 祝日マスタを書き出します。
   *
   * @param outputStream
   * @param
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  @Throws(IOException::class)
  fun writeToOutputStream(outputStream: OutputStream, criteria: HolidayCriteria, clazz: Class<*>) {
    Assert.notNull(criteria, "criteria must not be null")
    holidayRepository.findAll(criteria).use { data ->
      CsvUtils.writeCsv(outputStream, clazz, data) { holiday: Any ->
        modelMapper.map(holiday, clazz)
      }
    }
  }
}
