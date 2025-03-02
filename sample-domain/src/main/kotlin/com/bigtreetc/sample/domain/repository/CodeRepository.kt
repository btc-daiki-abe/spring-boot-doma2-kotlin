package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.domain.dao.CodeDao
import com.bigtreetc.sample.domain.entity.Code
import com.bigtreetc.sample.domain.entity.CodeCriteria
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.util.DomaUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/** コードマスタリポジトリ */
@Repository
class CodeRepository(private val codeDao: CodeDao) {

  /**
   * コードマスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: CodeCriteria, pageable: Pageable): Page<Code> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = codeDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * コードマスタを検索します。
   *
   * @return
   */
  fun findAll(criteria: CodeCriteria): Stream<Code> {
    return codeDao.selectAll(criteria)
  }

  /**
   * コードマスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: CodeCriteria): Result<Code> {
    return codeDao.select(criteria)
  }

  /**
   * コードマスタを1件取得します。
   *
   * @return
   */
  fun findById(id: Long): Code {
    return codeDao.selectById(id).getOrElse {
      throw NoDataFoundException("code_id=$id のデータが見つかりません。")
    }
  }

  /**
   * コードマスタを登録します。
   *
   * @param inputCode
   * @return
   */
  fun create(inputCode: Code): Code {
    codeDao.insert(inputCode)
    return inputCode
  }

  /**
   * コードマスタを更新します。
   *
   * @param inputCode
   * @return
   */
  fun update(inputCode: Code): Code {
    val updated = codeDao.update(inputCode)

    if (updated < 1) {
      throw NoDataFoundException("code_id=" + inputCode.id + " のデータが見つかりません。")
    }

    return inputCode
  }

  /**
   * コードマスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Code {
    val code =
      codeDao.selectById(id).getOrElse { throw NoDataFoundException("code_id=$id のデータが見つかりません。") }

    val updated = codeDao.delete(code)

    if (updated < 1) {
      throw NoDataFoundException("code_id=$id は更新できませんでした。")
    }

    return code
  }
}
