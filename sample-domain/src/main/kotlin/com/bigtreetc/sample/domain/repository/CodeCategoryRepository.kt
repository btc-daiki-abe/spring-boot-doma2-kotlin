package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.domain.dao.CodeCategoryDao
import com.bigtreetc.sample.domain.entity.CodeCategory
import com.bigtreetc.sample.domain.entity.CodeCategoryCriteria
import com.bigtreetc.sample.domain.exception.NoDataFoundException
import com.bigtreetc.sample.domain.util.DomaUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

/** コード分類マスタリポジトリ */
@Repository
class CodeCategoryRepository(private val codeCategoryDao: CodeCategoryDao) {

  /**
   * コード分類マスタを全件取得します。
   *
   * @return
   */
  fun fetchAll(): List<CodeCategory> {
    val pageable = Pageable.unpaged()
    val options = DomaUtils.createSelectOptions(pageable).count()
    return codeCategoryDao.selectAll(CodeCategoryCriteria(), options, Collectors.toList())
  }

  /**
   * コード分類マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: CodeCategoryCriteria, pageable: Pageable): Page<CodeCategory> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = codeCategoryDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * コード分類マスタを検索します。
   *
   * @param criteria
   * @return
   */
  fun findAll(criteria: CodeCategoryCriteria): Stream<CodeCategory> {
    return codeCategoryDao.selectAll(criteria)
  }

  /**
   * コード分類マスタを取得します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: CodeCategoryCriteria): CodeCategory? {
    return codeCategoryDao.select(criteria).getOrNull()
  }

  /**
   * コード分類マスタを取得します。
   *
   * @return
   */
  fun findById(id: Long): CodeCategory {
    return codeCategoryDao.selectById(id).getOrElse {
      throw NoDataFoundException("codeCategory_id=$id のデータが見つかりません。")
    }
  }

  /**
   * コード分類マスタを登録します。
   *
   * @param inputCodeCategory
   * @return
   */
  fun create(inputCodeCategory: CodeCategory): CodeCategory {
    codeCategoryDao.insert(inputCodeCategory)
    return inputCodeCategory
  }

  /**
   * コード分類マスタを更新します。
   *
   * @param inputCodeCategory
   * @return
   */
  fun update(inputCodeCategory: CodeCategory): CodeCategory {
    val updated = codeCategoryDao.update(inputCodeCategory)

    if (updated < 1) {
      throw NoDataFoundException("code_category_id=" + inputCodeCategory.id + " のデータが見つかりません。")
    }

    return inputCodeCategory
  }

  /**
   * コード分類マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): CodeCategory {
    val codeCategory =
      codeCategoryDao.selectById(id).getOrElse {
        throw NoDataFoundException("code_category_id=$id のデータが見つかりません。")
      }

    val updated = codeCategoryDao.delete(codeCategory)

    if (updated < 1) {
      throw NoDataFoundException("code_category_id=$id は更新できませんでした。")
    }

    return codeCategory
  }
}
