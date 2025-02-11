import com.diffplug.spotless.LineEnding
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.ir.backend.js.compile

buildscript {
	dependencies {
		classpath("com.google.cloud.tools:jib-spring-boot-extension-gradle:0.1.0")
	}
}

plugins {
	// apply false:
	// These plugins are not automatically applied.
	// They can be applied in subprojects as needed (in their respective build files).
	kotlin("jvm")
	kotlin("plugin.spring")
	id("org.springframework.boot")
	id("io.spring.dependency-management") version "1.1.7" // apply false
	id("org.owasp.dependencycheck") version "11.1.1" apply false
	id("org.domaframework.doma.compile") apply false
	id("org.graalvm.buildtools.native") version "0.10.4" apply false
	id("com.google.cloud.tools.jib") version "3.4.4" apply false
	id("com.diffplug.spotless") version "6.18.0" // apply false
	id("com.avast.gradle.docker-compose") version "0.17.12" apply false
//	id("com.github.ben-manes.versions") version "0.50.0" apply false
	id("jacoco")
}

group = "com.bigtreetc"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
	mavenLocal()
	maven(url = "https://repo.spring.io/release")
	maven(url = "https://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-parameters", "-Xlint:all", "-Xjsr305=strict")
	}
}

subprojects {
	apply(plugin = "kotlin")
	apply(plugin = "java")
	apply(plugin = "groovy")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.owasp.dependencycheck")
	apply(plugin = "com.diffplug.spotless")
//	apply (plugin = "com.github.ben-manes.versions")
	apply(plugin = "jacoco")

	repositories {
		mavenCentral()
		mavenLocal()
		maven(url = "https://repo.spring.io/release")
		maven(url = "https://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/")
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}

	spotless {
		val ktfmtVersion = "0.54"
		kotlin {
			target("**/*.kt")
			targetExclude("**/build/**/*.kt")
			ktfmt(ktfmtVersion).googleStyle()
			trimTrailingWhitespace()
			endWithNewline()
		}

		kotlinGradle {
			target("*.gradle.kts")
			ktfmt(ktfmtVersion).googleStyle()
		}

		groovy {
			trimTrailingWhitespace()
			indentWithSpaces(4)
			endWithNewline()
		}

		format("misc") {
			target("**/*.md", "**/*.yml")
			trimTrailingWhitespace()
			indentWithSpaces(2)
			endWithNewline()
		}

		lineEndings = LineEnding.UNIX
	}

	tasks.withType<GroovyCompile> {
		options.compilerArgs.addAll(arrayOf("-parameters", "-Xlint:all"))
	}

	dependencies {
		testCompileOnly("org.projectlombok:lombok")
		testAnnotationProcessor("org.projectlombok:lombok")

		val spockVersion = "2.4-M4-groovy-4.0"
		testImplementation("org.springframework.security:spring-security-test")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.spockframework:spock-core:${spockVersion}")
		testImplementation("org.spockframework:spock-spring:${spockVersion}")

		runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")
	}

	tasks.test {
		useJUnitPlatform()
		testLogging {
			events("passed", "skipped", "failed")
			exceptionFormat = TestExceptionFormat.FULL
			showCauses = true
			showExceptions = true
			showStackTraces = true
		}
		finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
	}

	tasks.jacocoTestReport {
		dependsOn(tasks.test) // tests are required to run before generating the report
	}

	// FIXME after sample-common setup
//	tasks.compileKotlin{
//		dependsOn(tasks.spotlessApply)
//	}
}
