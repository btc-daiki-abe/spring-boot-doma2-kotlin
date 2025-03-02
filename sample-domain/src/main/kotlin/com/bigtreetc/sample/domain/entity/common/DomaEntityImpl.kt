package com.bigtreetc.sample.domain.entity.common

import com.bigtreetc.sample.domain.dao.DefaultEntityListener
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import lombok.Getter
import lombok.Setter
import org.seasar.doma.Column
import org.seasar.doma.Entity
import org.seasar.doma.Transient
import org.seasar.doma.Version

@Entity(listener = DefaultEntityListener::class) // 自動的にシステム制御項目を更新するためにリスナーを指定する
@Setter
@Getter
abstract class DomaEntityImpl : DomaEntity {
  // 作成者
  @Column(updatable = false) @JsonIgnore override var createdBy: String? = null

  // 作成日時
  @Column(updatable = false) override var createdAt: LocalDateTime? = null

  // 更新者
  @JsonIgnore override var updatedBy: String? = null

  // 更新日時
  override var updatedAt: LocalDateTime? = null

  // 削除者
  @JsonIgnore override var deletedBy: String? = null

  // 削除日時
  override var deletedAt: LocalDateTime? = null

  // 楽観的排他制御で使用する改定番号
  @Version @Column(name = "version") override var version: Int? = null

  // 作成・更新者に使用する値
  @Transient @JsonIgnore var auditUser: String? = null

  // 作成・更新日に使用する値
  @Transient @JsonIgnore var auditDateTime: LocalDateTime? = null
}
