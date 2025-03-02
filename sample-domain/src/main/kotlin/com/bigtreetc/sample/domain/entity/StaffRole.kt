package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "staff_roles")
@Entity
@Getter
@Setter
open class StaffRole : DomaEntityImpl() {
  // 担当者ロールID
  @Id
  @Column(name = "staff_role_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // 担当者ID
  var staffId: Long? = null

  // ロールコード
  var roleCode: String? = null

  // ロール名
  var roleName: String? = null

  // 権限コード
  var permissionCode: String? = null

  // 権限名
  var permissionName: String? = null
}
