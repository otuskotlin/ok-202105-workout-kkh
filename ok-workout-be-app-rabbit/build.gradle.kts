plugins {
    kotlin("jvm")
}

dependencies {

    val coroutinesVersion: String by project
    val jacksonVersion: String by project
    val rabbitVersion: String by project
    val slf4jVersion: String by project
    val testcontainersVersion: String by project

    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")

    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-service-openapi"))
    implementation(project(":ok-workout-be-transport-openapi"))
    implementation(project(":ok-workout-be-mapping"))
    implementation(project(":ok-workout-be-logics"))

    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    testImplementation("org.testcontainers:rabbitmq:$testcontainersVersion")
    testImplementation(kotlin("test"))
}