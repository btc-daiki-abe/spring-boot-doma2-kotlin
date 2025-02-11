package com.bigtreetc.sample.common.util

import java.util.*
import java.util.function.Predicate
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment

object EnvironmentUtils {
  private val log = LoggerFactory.getLogger(CompressUtils::class.java)

  private var isLocalOrTest: Boolean = false
    private set
    get

  fun init(environment: Environment) {
    val isLocal = Predicate { profile: String -> profile == "local" }
    val isTest = Predicate { profile: String -> profile == "test" }
    val isLocalOrTest =
      if (environment.activeProfiles.size > 0) {
        Arrays.stream(environment.activeProfiles).anyMatch(isLocal.or(isTest))
      } else {
        true
      }

    log.info("isLocalOrTest: {}", isLocalOrTest)
    this.isLocalOrTest = isLocalOrTest
  }
}
