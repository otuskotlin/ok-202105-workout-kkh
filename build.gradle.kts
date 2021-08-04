plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    id("org.openapi.generator") apply false
}

group = "ru.otus.otuskotlin.workout"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}