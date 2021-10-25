plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    val testContainersVersion: String by project
    val cassandraDriverVersion: String by project
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesVersion")

    implementation("com.datastax.oss:java-driver-core:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-query-builder:$cassandraDriverVersion")

    kapt("com.datastax.oss:java-driver-mapper-processor:$cassandraDriverVersion")
    implementation("com.datastax.oss:java-driver-mapper-runtime:$cassandraDriverVersion")

    implementation(project(":ok-workout-be-common"))

    testImplementation("org.testcontainers:cassandra:$testContainersVersion")
    testImplementation(project(":ok-workout-be-repo-test"))
}