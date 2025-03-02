package com.bigtreetc.sample.domain.service.code

import com.bigtreetc.sample.domain.entity.*
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.repository.CodeRepository
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

/** コードマスタサービス */
@RequiredArgsConstructor
@Service
class CodeService(private val codeRepository: CodeRepository) : BaseTransactionalService() {

  /**
   * コードマスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: CodeCriteria, pageable: Pageable): Page<Code> {
    Assert.notNull(criteria, "criteria must not be null")
    return codeRepository.findAll(criteria, pageable)
  }

  /**
   * コードマスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: CodeCriteria): Result<Code> {
    Assert.notNull(criteria, "criteria must not be null")
    return codeRepository.findOne(criteria)
  }

  /**
   * コードマスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): Code {
    Assert.notNull(id, "id must not be null")
    return codeRepository.findById(id)
  }

  /**
   * コードを取得します。
   *
   * @param categoryCode
   * @return
   */
  @Transactional(readOnly = true)
  fun findByCategoryCode(categoryCode: String): Code {
    Assert.notNull(categoryCode, "categoryCode must not be null")
    val criteria: CodeCriteria = CodeCriteria()
    criteria.categoryCode = categoryCode
    return codeRepository.findOne(criteria).getOrElse {
      throw NoDataFoundException("category_code=$categoryCode のデータが見つかりません。")
    }
  }

  /**
   * コードを追加します。
   *
   * @param inputCode
   * @return
   */
  fun create(inputCode: Code): Code {
    Assert.notNull(inputCode, "inputCode must not be null")
    return codeRepository.create(inputCode)
  }

  /**
   * コードマスタを更新します。
   *
   * @param inputCode
   * @return
   */
  fun update(inputCode: Code): Code {
    Assert.notNull(inputCode, "inputCode must not be null")
    return codeRepository.update(inputCode)
  }

  /**
   * コードマスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): Code {
    Assert.notNull(id, "id must not be null")
    return codeRepository.delete(id)
  }

  /**
   * コードマスタを書き出します。
   *
   * @param outputStream
   * @param
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  @Throws(IOException::class)
  fun writeToOutputStream(outputStream: OutputStream, criteria: CodeCriteria, clazz: Class<*>) {
    Assert.notNull(criteria, "criteria must not be null")
    codeRepository.findAll(criteria).use { data ->
      CsvUtils.writeCsv(outputStream, clazz, data) { code: Any? -> modelMapper.map(code, clazz) }
    }
  }
}
