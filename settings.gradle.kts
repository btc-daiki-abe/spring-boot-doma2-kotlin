dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        mavenLocal()
        maven(url = "https://repo.spring.io/release")
        maven(url = "https://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/")
    }
}

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val spotlessVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("org.domaframework.doma.compile") version "3.0.1"
        id("com.diffplug.spotless") version spotlessVersion
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    }
}

rootProject.name = "sample"
include("sample-common")
include("sample-domain")
