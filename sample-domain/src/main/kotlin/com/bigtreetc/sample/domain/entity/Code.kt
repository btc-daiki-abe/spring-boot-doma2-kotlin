package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "codes")
// Bootrun時にオブジェクト作成
@Entity(immutable = false, metamodel = Metamodel())
@Getter
@Setter
open class Code : DomaEntityImpl() {
  // コードID
  @Id
  @Column(name = "code_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // 分類コード
  var categoryCode: String? = null

  // コード分類名
  @Column(insertable = false, updatable = false) var categoryName: String? = null

  // コード値
  var codeValue: String? = null

  // コード名
  var codeName: String? = null

  // エイリアス
  var codeAlias: String? = null

  // 表示順
  var displayOrder: Int? = null
}
