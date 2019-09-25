import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    compile("org.springframework.boot:spring-boot-starter")
    compile("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    compile("org.elasticsearch.client:elasticsearch-rest-high-level-client")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true