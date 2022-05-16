import org.jetbrains.kotlin.daemon.common.isDaemonEnabled
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.6.7"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  id("org.flywaydb.flyway") version "8.2.0"
  kotlin("jvm") version "1.6.21"
  kotlin("plugin.spring") version "1.6.21"
}

group = "by.butrameev"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("org.springframework.boot:spring-boot-starter-mail:2.6.7")
  implementation("org.springframework.data:spring-data-r2dbc:1.4.4")
  implementation("org.apache.httpcomponents:httpclient:4.5.13")
  implementation("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
  implementation("mysql:mysql-connector-java:8.0.29")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  implementation("org.flywaydb:flyway-core:8.5.10")
  implementation("org.flywaydb:flyway-mysql:8.5.10")
  implementation("org.flywaydb:flyway-sqlserver:8.5.10")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

flyway {
  url = "jdbc:mysql://localhost:3306/cryptowatch?useSSL=false"
  connectRetries = 10
  schemas = arrayOf("cryptowatch")
  user = "root"
  password = "xkmjr3m3"
  locations = arrayOf("filesystem:src/main/resources/db/migration")
}


