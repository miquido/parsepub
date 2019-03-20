import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:0.9.17")
    }
}

plugins {
    application
    kotlin("jvm") version "1.3.20"
    maven
    id("org.jetbrains.dokka") version "0.9.17"
}

group = "com.miquido"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    mavenLocal()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.miquido.parsepub.MainKt"
}
val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "doc"
    includeNonPublic = false
    reportUndocumented = true
}

dependencies {
    ImplementationDependencies().forEach {
        implementation("${it.key}:${it.value}")
    }

    TestImplementationDependencies().forEach {
        testImplementation("${it.key}:${it.value}")
    }
}

class ImplementationDependencies : HashMap<String, String>() {

    init {
        put("org.jetbrains.kotlin:kotlin-stdlib-jdk8", KOTLIN_STDLIB_VERSION)
    }

    companion object {
        private const val KOTLIN_STDLIB_VERSION = ""
    }
}

class TestImplementationDependencies : HashMap<String, String>() {

    init {
        put("org.jetbrains.kotlin:kotlin-test", KOTLIN_TEST_VERSION)
        put("org.jetbrains.kotlin:kotlin-test-junit", KOTLIN_TEST_JUNIT_VERSION)
        put("org.assertj:assertj-core", ASSERJ_VERSION)
        put("com.nhaarman.mockitokotlin2:mockito-kotlin", MOCKITO_VERSION)
    }

    companion object {
        private const val KOTLIN_TEST_VERSION = ""
        private const val KOTLIN_TEST_JUNIT_VERSION = ""
        private const val JUNIT_VERSION = "5.2.0"
        private const val ASSERJ_VERSION = "3.11.1"
        private const val MOCKITO_VERSION = "2.1.0"
    }
}