package com.bigtreetc.sample.domain.entity

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class RolePermissionCriteria : RolePermission() {
  var roleCodes: Collection<String>? = null
}
