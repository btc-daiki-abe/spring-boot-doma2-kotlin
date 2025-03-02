package com.bigtreetc.sample.domain.entity.common

import java.time.LocalDateTime

// TODO: コメントを書く
interface DomaEntity : IEntity {
  var createdBy: String?

  var createdAt: LocalDateTime?

  var updatedBy: String?

  var updatedAt: LocalDateTime?

  var deletedBy: String?

  var deletedAt: LocalDateTime?

  var version: Int?
}
