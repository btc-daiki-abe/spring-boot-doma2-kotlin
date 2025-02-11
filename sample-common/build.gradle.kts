plugins { kotlin("jvm") }

group = "com.bigtreetc"

version = "0.0.1-SNAPSHOT"

dependencies {
  api("org.springframework.boot:spring-boot-starter")

  api("org.apache.commons:commons-lang3")
  api("org.apache.commons:commons-text:1.11.0")
  api("org.apache.commons:commons-compress:1.25.0")
  api("commons-codec:commons-codec")
  api("org.apache.commons:commons-digester3:3.2")
  api("commons-io:commons-io:2.15.1")
  api("com.ibm.icu:icu4j:74.2")

  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }
