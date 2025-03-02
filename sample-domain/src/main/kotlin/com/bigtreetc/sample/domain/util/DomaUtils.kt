package com.bigtreetc.sample.domain.util

import org.seasar.doma.jdbc.SelectOptions
import org.springframework.data.domain.Pageable

/** Doma関連ユーティリティ */
object DomaUtils {
  /**
   * SearchOptionsを作成して返します。
   *
   * @return
   */
  fun createSelectOptions(): SelectOptions {
    return SelectOptions.get()
  }

  /**
   * SearchOptionsを作成して返します。
   *
   * @param pageable
   * @return
   */
  fun createSelectOptions(pageable: Pageable): SelectOptions {
    if (pageable.isUnpaged) {
      return SelectOptions.get()
    }
    val page = pageable.pageNumber
    val perpage = pageable.pageSize
    return createSelectOptions(page, perpage)
  }

  /**
   * SearchOptionsを作成して返します。
   *
   * @param page
   * @param perpage
   * @return
   */
  fun createSelectOptions(page: Int, perpage: Int): SelectOptions {
    val offset = page * perpage
    return SelectOptions.get().offset(offset).limit(perpage)
  }
}
