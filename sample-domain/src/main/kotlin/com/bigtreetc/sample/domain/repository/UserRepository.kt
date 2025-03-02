package com.bigtreetc.sample.domain.repository

import com.bigtreetc.sample.domain.dao.UploadFileDao
import com.bigtreetc.sample.domain.dao.UserDao
import com.bigtreetc.sample.domain.dao.UserRoleDao
import com.bigtreetc.sample.domain.entity.User
import com.bigtreetc.sample.domain.entity.UserCriteria
import com.bigtreetc.sample.domain.entity.UserRole
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

/** 顧客マスタリポジトリ */
@RequiredArgsConstructor
@Repository
class UserRepository(
  private val userDao: UserDao,
  private val userRoleDao: UserRoleDao,
  private val uploadFileDao: UploadFileDao,
) {

  /**
   * 顧客マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  fun findAll(criteria: UserCriteria, pageable: Pageable): Page<User> {
    val options = DomaUtils.createSelectOptions(pageable).count()
    val data = userDao.selectAll(criteria, options, Collectors.toList())
    return PageImpl(data, pageable, options.count)
  }

  /**
   * 顧客マスタを検索します。
   *
   * @return
   */
  fun findAll(criteria: UserCriteria): Stream<User> {
    return userDao.selectAll(criteria)
  }

  /**
   * 顧客マスタを1件取得します。
   *
   * @param criteria
   * @return
   */
  fun findOne(criteria: UserCriteria): Result<User> {
    val user = userDao.select(criteria)

    // 添付ファイルを取得する
    user.onSuccess { u: User ->
      val uploadFile = u.uploadFileId?.let { uploadFileDao.selectById(it) }
      u.uploadFile = uploadFile
    }

    return user
  }

  /**
   * 顧客マスタを1件取得します。
   *
   * @return
   */
  fun findById(id: Long): User {
    val user =
      userDao.selectById(id).getOrElse { throw NoDataFoundException("user_id=$id のデータが見つかりません。") }

    // 添付ファイルを取得する
    user.uploadFile = user.uploadFileId?.let { uploadFileDao.selectById(it) }

    return user
  }

  /**
   * 顧客マスタを登録します。
   *
   * @param inputUser
   * @return
   */
  fun create(inputUser: User): User {
    userDao.insert(inputUser)

    // ロール権限紐付けを登録する
    val userRole = UserRole()
    userRole.userId = inputUser.id
    userRole.roleCode = "user"
    userRoleDao.insert(userRole)

    return inputUser
  }

  /**
   * 顧客マスタを更新します。
   *
   * @param inputUser
   * @return
   */
  fun update(inputUser: User): User {
    inputUser.uploadFile?.let {
      // 添付ファイルがある場合は、登録・更新する
      val uploadFileId = inputUser.uploadFileId
      if (uploadFileId == null) {
        uploadFileDao.insert(it)
      } else {
        uploadFileDao.update(it)
      }
      inputUser.uploadFileId = it.id
    }

    val updated = userDao.update(inputUser)

    if (updated < 1) {
      throw NoDataFoundException("user_id=" + inputUser.id + " のデータが見つかりません。")
    }

    return inputUser
  }

  /**
   * 顧客マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): User {
    val user =
      userDao.selectById(id).getOrElse { throw NoDataFoundException("user_id=$id のデータが見つかりません。") }

    val updated = userDao.delete(user)

    if (updated < 1) {
      throw NoDataFoundException("user_id=$id は更新できませんでした。")
    }

    return user
  }
}
