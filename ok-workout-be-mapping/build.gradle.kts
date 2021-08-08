plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-transport-openapi"))
}