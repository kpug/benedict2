import io.gitlab.arturbosch.detekt.detekt
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.kotlin.dsl.dependencyManagement
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    kotlin("jvm") version Versions.kotlin apply false
    kotlin("plugin.spring") version Versions.kotlin apply false
    kotlin("plugin.allopen") version Versions.kotlin apply false
    kotlin("plugin.noarg") version Versions.kotlin apply false
    id("org.jetbrains.kotlin.kapt") version Versions.kotlin apply false

    id(BuildPlugins.detekt) version BuildPlugins.Versions.detekt apply false
    id(BuildPlugins.dokka) version BuildPlugins.Versions.dokka apply false
    id(BuildPlugins.dependency_management) version BuildPlugins.Versions.dependency_management

    id("org.springframework.boot") version Versions.spring_boot apply false
    id("com.gorylenko.gradle-git-properties") version "1.5.1" apply false
    id("com.palantir.docker") version "0.21.0" apply false
}

allprojects {
    val baseVersion: String by project
    val snapshotVersion: String by project

    group = "cc.kpug.benedict2"
    version = baseVersion + snapshotVersion

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {

    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()

        plugin(BuildPlugins.detekt)
        plugin(BuildPlugins.dokka)
        plugin(BuildPlugins.dependency_management)
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("com.gorylenko.gradle-git-properties")
        plugin("kotlin-kapt")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:${Versions.spring_boot}")
        }
    }
    dependencies {
        val api by configurations
        val compile by configurations
        val compileOnly by configurations
        val testCompile by configurations
        val implementation by configurations
        val testImplementation by configurations
        val testRuntimeOnly by configurations

        compile("com.fasterxml.jackson.module:jackson-module-kotlin")
        compile("org.jetbrains.kotlin:kotlin-reflect")
        compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        compile("org.springframework.boot:spring-boot-starter-logging")
        compileOnly("org.springframework.boot:spring-boot-configuration-processor")
        testCompile("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        sourceCompatibility = "1.8"
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    detekt {
        description = "Runs a failfast detekt build."

        input = files("src/main/kotlin")
        config = files("${project.rootProject.rootDir}/detekt.yml")
        filters = ".*/build/.*"

        reports {
            xml.enabled = false
            html.enabled = true
        }
    }
}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}



