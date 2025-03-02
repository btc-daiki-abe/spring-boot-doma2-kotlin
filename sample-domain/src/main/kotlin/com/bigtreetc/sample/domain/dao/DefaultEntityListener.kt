package com.bigtreetc.sample.domain.dao

import com.bigtreetc.sample.common.util.ReflectionUtils.findWithAnnotation
import com.bigtreetc.sample.common.util.ReflectionUtils.getFieldValue
import com.bigtreetc.sample.domain.entity.common.DomaEntity
import com.bigtreetc.sample.domain.entity.common.IEntity
import com.bigtreetc.sample.domain.exception.DoubleSubmitErrorException
import java.lang.reflect.Field
import java.util.stream.Collectors
import lombok.NoArgsConstructor
import org.seasar.doma.Id
import org.seasar.doma.jdbc.entity.EntityListener
import org.seasar.doma.jdbc.entity.PreDeleteContext
import org.seasar.doma.jdbc.entity.PreInsertContext
import org.seasar.doma.jdbc.entity.PreUpdateContext
import org.slf4j.LoggerFactory

@NoArgsConstructor // コンストラクタが必須のため
class DefaultEntityListener<ENTITY> : EntityListener<ENTITY> {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun preInsert(entity: ENTITY, context: PreInsertContext<ENTITY>) {
    // 二重送信防止チェック
    val expected = DoubleSubmitCheckTokenHolder.expectedToken
    val actual = DoubleSubmitCheckTokenHolder.actualToken

    if (expected != actual) {
      throw DoubleSubmitErrorException()
    }

    if (entity is DomaEntity) {
      val createdAt = AuditInfoHolder.auditDateTime
      val createdBy = AuditInfoHolder.auditUser

      entity.createdAt = createdAt // 作成日
      entity.createdBy = createdBy // 作成者
    }
  }

  override fun preUpdate(entity: ENTITY, context: PreUpdateContext<ENTITY>) {
    if (entity is DomaEntity) {
      val updatedAt = AuditInfoHolder.auditDateTime
      val updatedBy = AuditInfoHolder.auditUser

      val methodName = context.method.name
      if (methodName.startsWith("delete")) {
        entity.deletedAt = updatedAt // 削除日
        entity.deletedBy = updatedBy // 削除者
      } else {
        entity.updatedAt = updatedAt // 更新日
        entity.updatedBy = updatedBy // 更新者
      }
    }
  }

  override fun preDelete(entity: ENTITY, context: PreDeleteContext<ENTITY>) {
    if (entity is DomaEntity) {
      val deletedAt = AuditInfoHolder.auditDateTime
      val deletedBy = AuditInfoHolder.auditUser
      val name: String = entity.javaClass.name
      val ids = getIds(entity)

      // 物理削除した場合はログ出力する
      log.info(
        "データを物理削除しました。entity={}, id={}, deletedBy={}, deletedAt={}",
        name,
        ids,
        deletedBy,
        deletedAt,
      )
    }
  }

  /**
   * Idアノテーションが付与されたフィールドの値のリストを返します。
   *
   * @param entity
   * @return
   */
  protected fun getIds(entity: IEntity): List<Any> {
    return findWithAnnotation(entity.javaClass, Id::class.java)
      .map { f: Field -> getFieldValue(f!!, entity) }
      .collect(Collectors.toList())
  }
}
