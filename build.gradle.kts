import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val versionReleaseDate = "2019-05-20T13:00:00.000+0200"
val versionUserOrgName = "miquido"
val versionName = "parsepub"
val versionRepoName = "maven-repo"
val versionArtifactId = "parsepub"
val versionNumber = "1.0.0"
val versionGroupId = "com.miquido"
val versionPublicationName = "maven"
val versionPublicationDesc = "epub parser library"
val versionSiteUrl = "https://www.miquido.com"
val versionVcsUrl = "https://github.com/miquido/parsepub"
val versionGithubRepo = "miquido/parsepub"
val versionLicenseName = "The Apache Software License, Version 2.0"
val versionLicenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
val versionLabel = arrayOf("epub", "parser", "kotlin")
val versionAllLicenses = arrayOf("Apache-2.0")

val bintrayUser = (project.properties["BINTRAY_USER"] as String?).orEmpty()
val bintrayApiKey = (project.properties["BINTRAY_API_KEY"] as String?).orEmpty()

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
    `maven-publish`
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.jfrog.bintray") version "1.8.4"
}

publishing {
    publications {
        create<MavenPublication>(versionPublicationName) {
            groupId = versionGroupId
            artifactId = versionArtifactId
            version = versionNumber

            from(components["java"])

            pom.withXml({
                asNode().apply {
                    appendNode("description", versionPublicationDesc)
                    appendNode("name", versionName)
                    appendNode("url", versionSiteUrl)
                }
            })

        }

    }
}

bintray {
    user = bintrayUser
    key = bintrayApiKey
    setPublications(versionPublicationName)
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        userOrg = versionUserOrgName
        repo = versionRepoName
        websiteUrl = versionSiteUrl
        vcsUrl = versionVcsUrl
        name = versionArtifactId
        githubRepo = versionGithubRepo
        description = versionPublicationDesc
        desc = versionPublicationDesc
        setLabels(*versionLabel)
        setLicenses(*versionAllLicenses)
        version.apply {
            name = versionNumber
            desc = versionPublicationDesc
            released = versionReleaseDate
        }
    })
}

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