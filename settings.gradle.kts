pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
        mavenCentral()
        // for Avro plugin
        maven(url = "https://dl.bintray.com/gradle/gradle-plugins")
    }
}

rootProject.name = "benedict2"
include("benedict-core")
include("benedict-insertion-app")
include("benedict-retriever-app")