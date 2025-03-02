package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "code_categories")
@Entity
@Getter
@Setter
open class CodeCategory : DomaEntityImpl() {
  // コード分類ID
  @Id
  @Column(name = "code_category_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // 分類コード
  var categoryCode: String? = null

  // 分類名
  var categoryName: String? = null
}
