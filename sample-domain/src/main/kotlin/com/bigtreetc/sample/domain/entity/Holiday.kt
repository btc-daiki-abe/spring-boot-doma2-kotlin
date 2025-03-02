package com.bigtreetc.sample.domain.entity

import com.bigtreetc.sample.domain.entity.common.DomaEntityImpl
import java.time.LocalDate
import lombok.Getter
import lombok.Setter
import org.seasar.doma.*

@Table(name = "holidays")
@Entity
@Getter
@Setter
open class Holiday : DomaEntityImpl() {
  // 祝日ID
  @Id
  @Column(name = "holiday_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null

  // 祝日名
  var holidayName: String? = null

  // 日付
  var holidayDate: LocalDate? = null
}
