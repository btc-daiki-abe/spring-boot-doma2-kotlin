package com.bigtreetc.sample.domain.service.user

import com.bigtreetc.sample.domain.entity.*
import com.bigtreetc.sample.domain.repository.UserRepository
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

/** 顧客マスタサービス */
@RequiredArgsConstructor
@Service
class UserService(private val userRepository: UserRepository) : BaseTransactionalService() {

  /**
   * 顧客マスタを検索します。
   *
   * @param criteria
   * @param pageable
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  fun findAll(criteria: UserCriteria, pageable: Pageable): Page<User> {
    Assert.notNull(criteria, "criteria must not be null")
    return userRepository.findAll(criteria, pageable)
  }

  /**
   * 顧客マスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findOne(criteria: UserCriteria): Result<User> {
    Assert.notNull(criteria, "criteria must not be null")
    return userRepository.findOne(criteria)
  }

  /**
   * 顧客マスタを1件取得します。
   *
   * @return
   */
  @Transactional(readOnly = true)
  fun findById(id: Long): User {
    Assert.notNull(id, "id must not be null")
    return userRepository.findById(id)
  }

  /**
   * 顧客マスタを追加します。
   *
   * @param inputUser
   * @return
   */
  fun create(inputUser: User): User {
    Assert.notNull(inputUser, "inputUser must not be null")
    return userRepository.create(inputUser)
  }

  /**
   * 顧客マスタを更新します。
   *
   * @param inputUser
   * @return
   */
  fun update(inputUser: User): User {
    Assert.notNull(inputUser, "inputUser must not be null")

    val user = inputUser.id?.let { userRepository.findById(it) }
    if (user != null) {
      modelMapper.map(inputUser, user)
      return userRepository.update(user)
    }
    return User()
  }

  /**
   * 顧客マスタを論理削除します。
   *
   * @return
   */
  fun delete(id: Long): User {
    Assert.notNull(id, "id must not be null")
    return userRepository.delete(id)
  }

  /**
   * 顧客マスタを書き出します。
   *
   * @param outputStream
   * @param
   * @return
   */
  @Transactional(readOnly = true) // 読み取りのみの場合は指定する
  @Throws(IOException::class)
  fun writeToOutputStream(outputStream: OutputStream, criteria: UserCriteria, clazz: Class<*>) {
    Assert.notNull(criteria, "criteria must not be null")
    userRepository.findAll(criteria).use { data ->
      CsvUtils.writeCsv(outputStream, clazz, data) { user: Any? -> modelMapper.map(user, clazz) }
    }
  }
}
