import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.7.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("org.jetbrains.kotlin.jvm") version "1.3.21"
    id("org.jetbrains.kotlin.kapt") version "1.3.21"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.21" apply false
    id("com.gorylenko.gradle-git-properties") version "1.5.1" apply false
}

val baseVersion: String by project
val snapshotVersion: String by project

allprojects {
    group = "cc.kpug.benedict2"
    version = baseVersion + snapshotVersion

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "com.gorylenko.gradle-git-properties")
    apply {
        plugin<JavaLibraryPlugin>()
        plugin<KotlinPlatformJvmPlugin>()
    }

    tasks.withType<KotlinCompile> {
        sourceCompatibility = "1.8"
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
            jvmTarget = "1.8"
        }
    }

    val sourceSets = project.the<SourceSetContainer>()

    val sourcesJar by tasks.creating(Jar::class) {
        from(sourceSets["main"].allSource)
        archiveClassifier.set("sources")
    }

    tasks.withType<Test> {
        useJUnitPlatform()

        testLogging {
            events("failed")
        }
    }

    tasks["clean"].doLast {
        delete("./.project")
        delete("./out")
        delete("./bin")
    }

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }

}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}



