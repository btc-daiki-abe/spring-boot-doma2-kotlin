package com.bigtreetc.sample.domain.service

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired

abstract class BaseService {
  @Autowired protected lateinit var modelMapper: ModelMapper
}
