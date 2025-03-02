package com.bigtreetc.sample.domain

import com.bigtreetc.sample.domain.entity.common.DomaEntity
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies

/** ModelMapperのファクトリ */
object DefaultModelMapperFactory {
  /**
   * ModelMapperを返します。
   *
   * @return
   */
  fun create(): ModelMapper {
    // ObjectMappingのためのマッパー
    val modelMapper = ModelMapper()
    val configuration = modelMapper.configuration

    configuration.setPropertyCondition {
      // IDフィールド以外をマッピングする
      // DomaDtoのIDカラムは上書きしないようにする
      val propertyInfo = it.mapping.lastDestinationProperty
      !(it.parent.destination is DomaEntity && propertyInfo.name == "id")
    }

    // 厳格にマッピングする
    configuration.setMatchingStrategy(MatchingStrategies.STRICT)
    configuration.setFullTypeMatchingRequired(true)

    return modelMapper
  }
}
