import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.20"
}

group = "com.miquido"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "com.miquido.parsepub.MainKt"
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
        put("org.simpleframework:simple-xml", XML_PARSER_VERSION)
        put("org.koin:koin-core", KOIN_VERSION)
        put("org.slf4j:slf4j-api", LOGGER_VERSION)
        put("org.slf4j:slf4j-simple", LOGGER_VERSION)
        put("org.slf4j:slf4j-nop", LOGGER_VERSION)
    }

    companion object {
        const val KOTLIN_STDLIB_VERSION = ""
        const val XML_PARSER_VERSION = "2.7.1"
        const val KOIN_VERSION = "1.0.2"
        const val LOGGER_VERSION = "1.7.25"
    }
}

class TestImplementationDependencies : HashMap<String, String>() {
    init {
        put("org.jetbrains.kotlin:kotlin-test", KOTLIN_TEST_VERSION)
        put("org.jetbrains.kotlin:kotlin-test-junit", KOTLIN_TEST_JUNIT_VERSION)
        put("org.koin:koin-test", KOIN_VERSION)
    }

    companion object {
        const val KOTLIN_TEST_VERSION = ""
        const val KOTLIN_TEST_JUNIT_VERSION = ""
        const val KOIN_VERSION = "1.0.2"
    }
}