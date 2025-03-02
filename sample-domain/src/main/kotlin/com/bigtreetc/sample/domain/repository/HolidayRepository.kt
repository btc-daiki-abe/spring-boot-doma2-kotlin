package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.domain.dao.HolidayDao
import com.bigtreetc.sample.domain.entity.Holiday
import com.bigtreetc.sample.domain.entity.HolidayCriteria
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

/** 祝日マスタリポジトリ */
@RequiredArgsConstructor
@Repository
class HolidayRepository(private val holidayDao: HolidayDao) {

  /**
   * 祝日マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: HolidayCriteria, pageable: Pageable): Page<Holiday> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = holidayDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * 祝日マスタを検索します。
   *
   * @return
   */
  fun findAll(criteria: HolidayCriteria): Stream<Holiday> {
    return holidayDao.selectAll(criteria)
  }

  /**
   * 祝日マスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: HolidayCriteria): Result<Holiday> {
    return holidayDao.select(criteria)
  }

  /**
   * 祝日マスタを1件取得します。
   *
   * @return
   */
  fun findById(id: Long): Holiday {
    return holidayDao.selectById(id).getOrElse {
      throw NoDataFoundException("holiday_id=$id のデータが見つかりません。")
    }
  }

  /**
   * 祝日マスタを登録します。
   *
   * @param inputHoliday
   * @return
   */
  fun create(inputHoliday: Holiday): Holiday {
    holidayDao.insert(inputHoliday)
    return inputHoliday
  }

  /**
   * 祝日マスタを更新します。
   *
   * @param inputHoliday
   * @return
   */
  fun update(inputHoliday: Holiday): Holiday {
    val updated = holidayDao.update(inputHoliday)

    if (updated < 1) {
      throw NoDataFoundException("holiday_id=" + inputHoliday.id + " のデータが見つかりません。")
    }

    return inputHoliday
  }

  /**
   * 祝日マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Holiday {
    val holiday =
      holidayDao.selectById(id).getOrElse {
        throw NoDataFoundException("holiday_id=$id のデータが見つかりません。")
      }

    val updated = holidayDao.delete(holiday)

    if (updated < 1) {
      throw NoDataFoundException("holiday_id=$id は更新できませんでした。")
    }

    return holiday
  }
}
