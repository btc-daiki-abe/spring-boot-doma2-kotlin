pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val doma2Version: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("org.domaframework.doma.compile") version doma2Version
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "sample"
include("sample-common")
include("sample-domain")
