package com.bigtreetc.sample.domain.service.codecategory

import com.bigtreetc.sample.domain.entity.CodeCategory
import com.bigtreetc.sample.domain.entity.CodeCategoryCriteria
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.repository.CodeCategoryRepository
import com.bigtreetc.sample.domain.service.BaseTransactionalService
import com.bigtreetc.sample.domain.util.CsvUtils
import java.io.IOException
import java.io.OutputStream
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.Assert

/** コード分類マスタサービス */
@Service
class CodeCategoryService(private val codeCategoryRepository: CodeCategoryRepository) :
  BaseTransactionalService() {

  /**
   * コード分類マスタマスタを全件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun fetchAll(): List<CodeCategory> {
    return codeCategoryRepository.fetchAll()
  }

  /**
   * コード分類マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: CodeCategoryCriteria, pageable: Pageable): Page<CodeCategory> {
    Assert.notNull(criteria, "criteria must not be null")
    return codeCategoryRepository.findAll(criteria, pageable)
  }

  /**
   * コード分類マスタを取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: CodeCategoryCriteria): CodeCategory? {
    Assert.notNull(criteria, "criteria must not be null")
    return codeCategoryRepository.findOne(criteria)
  }

  /**
   * コード分類マスタを取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): CodeCategory {
    Assert.notNull(id, "id must not be null")
    return codeCategoryRepository.findById(id)
  }

  /**
   * コード分類マスタを取得します。
   *
   * @param categoryCode
   * @return
   */
  @Transactional(readOnly = true)
  fun findByCategoryCode(categoryCode: String): CodeCategory {
    Assert.notNull(categoryCode, "categoryCode must not be null")

    val criteria: CodeCategoryCriteria = CodeCategoryCriteria()
    criteria.categoryCode = categoryCode

    return codeCategoryRepository.findOne(criteria)
      ?: throw NoDataFoundException("category_code=$categoryCode のデータが見つかりません。")
  }

  /**
   * コード分類マスタを登録します。
   *
   * @param inputCodeCategory
   * @return
   */
  fun create(inputCodeCategory: CodeCategory): CodeCategory {
    Assert.notNull(inputCodeCategory, "inputCodeCategory must not be null")
    return codeCategoryRepository.create(inputCodeCategory)
  }

  /**
   * コード分類マスタを更新します。
   *
   * @param inputCodeCategory
   * @return
   */
  fun update(inputCodeCategory: CodeCategory): CodeCategory {
    Assert.notNull(inputCodeCategory, "inputCodeCategory must not be null")
    return codeCategoryRepository.update(inputCodeCategory)
  }

  /**
   * コード分類マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): CodeCategory {
    Assert.notNull(id, "id must not be null")
    return codeCategoryRepository.delete(id)
  }

  /**
   * コード分類マスタを書き出します。
   *
   * @param outputStream
   * @param
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  @Throws(IOException::class)
  fun writeToOutputStream(
    outputStream: OutputStream,
    criteria: CodeCategoryCriteria,
    clazz: Class<*>,
  ) {
    Assert.notNull(criteria, "criteria must not be null")
    codeCategoryRepository.findAll(criteria).use { data ->
      CsvUtils.writeCsv(outputStream, clazz, data) { codeCategory: Any? ->
        modelMapper.map(codeCategory, clazz)
      }
    }
  }
}
