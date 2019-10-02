import com.palantir.gradle.docker.DockerExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

apply(plugin = "com.palantir.docker")
val bootJar: BootJar by tasks
bootJar.enabled = true

configure<DockerExtension> {

    name = "${project.name}"
    version = "${project.version}"
    files(bootJar.archivePath)
    setDockerfile(file("src/main/docker/Dockerfile"))
    buildArgs(mapOf(
            "JAVA_OPTS" to "-Xs64m -Xmx128m",
            "JAR_FILE" to "${bootJar.archiveName}"
    ))
    dependsOn(tasks["build"])
}

dependencies {
    compile(project(":benedict-core"))

    compile(kotlin("stdlib-jdk8"))
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter")
    compile("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}