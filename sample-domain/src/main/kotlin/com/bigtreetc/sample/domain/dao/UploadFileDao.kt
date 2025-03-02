package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.domain.entity.UploadFile
import com.bigtreetc.sample.domain.entity.UploadFileCriteria
import org.seasar.doma.*
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.SelectOptions

@ConfigAutowireable
@Dao
interface UploadFileDao {
  /**
   * アップロードファイルを取得します。
   *
   * @param criteria
   * @param options
   * @return
   */
  @Select fun selectAll(criteria: UploadFileCriteria, options: SelectOptions): List<UploadFile>

  /**
   * アップロードファイルを1件取得します。
   *
   * @param id
   * @return
   */
  @Select fun selectById(id: Long): UploadFile

  /**
   * アップロードファイルを1件取得します。
   *
   * @param criteria
   * @return
   */
  @Select fun select(criteria: UploadFileCriteria): UploadFile

  /**
   * アップロードファイルを登録します。
   *
   * @param uploadFile
   * @return
   */
  @Insert fun insert(uploadFile: UploadFile): Int

  /**
   * アップロードファイルを更新します。
   *
   * @param uploadFile
   * @return
   */
  @Update fun update(uploadFile: UploadFile): Int

  /**
   * アップロードファイルを物理削除します。
   *
   * @param uploadFile
   * @return
   */
  @Update(excludeNull = true) fun delete(uploadFile: UploadFile): Int

  /**
   * アップロードファイルを一括登録します。
   *
   * @param uploadFiles
   * @return
   */
  @BatchInsert fun insert(uploadFiles: List<UploadFile>): IntArray
}
