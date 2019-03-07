import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.20"
    maven
}

group = "com.miquido"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    mavenLocal()
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
    }

    companion object {
        private const val KOTLIN_TEST_VERSION = ""
        private const val KOTLIN_TEST_JUNIT_VERSION = ""
        private const val JUNIT_VERSION = "5.2.0"
        private const val ASSERJ_VERSION = "3.11.1"
    }
}