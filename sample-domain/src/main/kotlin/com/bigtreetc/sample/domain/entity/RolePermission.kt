package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "role_permissions")
@Entity
@Getter
@Setter
open class RolePermission : DomaEntityImpl() {
  @OriginalStates
  var originalStates: // 差分UPDATEのために定義する
    RolePermission? =
    null

  @Id
  @Column(name = "role_permission_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // ロールコード
  var roleCode: String? = null

  // 権限コード
  var permissionCode: String? = null

  // 有効
  var isEnabled: Boolean? = null
}
