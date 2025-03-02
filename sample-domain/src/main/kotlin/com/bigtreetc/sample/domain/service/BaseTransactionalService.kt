package com.bigtreetc.sample.domain.service

import org.springframework.transaction.annotation.Transactional

@Transactional(rollbackFor = [Throwable::class])
abstract class BaseTransactionalService : BaseService()
