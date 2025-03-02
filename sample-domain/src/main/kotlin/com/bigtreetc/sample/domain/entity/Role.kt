package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.common.util.ValidateUtils.isEquals
import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import kotlin.jvm.Transient
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "roles")
@Entity
@Getter
@Setter
open class Role : DomaEntityImpl() {
  @OriginalStates
  var originalStates: // 差分UPDATEのために定義する
    Role? =
    null

  @Id
  @Column(name = "role_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // ロールコード
  var roleCode: String? = null

  // ロール名
  var roleName: String? = null

  @Transient val rolePermissions: List<RolePermission> = listOf()

  @Transient val permissions: List<Permission> = listOf()

  fun hasPermission(permissionCode: String?): Boolean {
    return rolePermissions.stream().anyMatch { rp: RolePermission ->
      (isEquals(rp.permissionCode!!, permissionCode!!) && rp.isEnabled == true)
    }
  }

  fun setPermission(permissionCode: String?, isEnabled: Boolean) {
    rolePermissions
      .stream()
      .filter { rp: RolePermission -> isEquals(rp.permissionCode!!, permissionCode!!) }
      .findFirst()
      .ifPresent { rp: RolePermission -> rp.isEnabled = isEnabled }
  }

  companion object {
    private const val serialVersionUID = 4825745231712286767L
  }
}
