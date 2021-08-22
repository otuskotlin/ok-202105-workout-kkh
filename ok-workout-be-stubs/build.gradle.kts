plugins {
    kotlin("jvm")
}

group = "ru.otus.otuskotlin.workout"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-workout-be-common"))
}