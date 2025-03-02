package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "user_roles")
@Entity
@Getter
@Setter
open class UserRole : DomaEntityImpl() {
  // 担当者ロールID
  @Id
  @Column(name = "user_role_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // ユーザーID
  var userId: Long? = null

  // ロールコード
  var roleCode: String? = null

  // ロール名
  var roleName: String? = null

  // 権限コード
  var permissionCode: String? = null

  // 権限名
  var permissionName: String? = null
}
