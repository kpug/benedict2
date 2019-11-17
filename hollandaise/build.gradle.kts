dependencies {
    compile(project(":benedict-core"))
    compile(kotlin("stdlib-jdk8"))

    compile("org.springframework.boot:spring-boot-starter")
    compile("org.apache.commons:commons-lang3:3.9")
    compile("commons-io:commons-io:2.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}