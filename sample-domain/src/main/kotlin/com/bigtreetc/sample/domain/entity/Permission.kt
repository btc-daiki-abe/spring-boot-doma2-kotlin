package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "permissions")
@Entity
@Getter
@Setter
open class Permission : DomaEntityImpl() {
  // 権限ID
  @Id
  @Column(name = "permission_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // 権限コード
  var permissionCode: String? = null

  // 権限名
  var permissionName: String? = null
}
