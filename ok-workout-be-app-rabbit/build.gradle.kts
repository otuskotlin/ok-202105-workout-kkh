plugins {
    kotlin("jvm")
}

dependencies {

    val coroutinesVersion: String by project
    val jacksonVersion: String by project
    val rabbitVersion: String by project
    val slf4jVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")

    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-service-openapi"))
    implementation(project(":ok-workout-be-transport-openapi"))
    implementation(project(":ok-workout-be-mapping"))
    implementation(project(":ok-workout-be-logics"))
    implementation(project(":ok-workout-be-stubs"))

    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
    testImplementation(kotlin("test"))
}