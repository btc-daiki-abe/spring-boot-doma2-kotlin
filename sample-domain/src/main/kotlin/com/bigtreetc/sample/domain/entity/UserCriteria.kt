package com.bigtreetc.sample.domain.entity

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class UserCriteria : User() {
  // 住所がNULLのデータに絞り込む
  var onlyNullAddress: Boolean? = null

  companion object {}
}
