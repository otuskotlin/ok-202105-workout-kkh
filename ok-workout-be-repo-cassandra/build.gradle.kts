plugins {
    kotlin("jvm")
}

dependencies {
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":ok-workout-be-common"))

    testImplementation("org.testcontainers:cassandra:$testContainersVersion")
    testImplementation(project(":ok-workout-be-repo-test"))
}