val ktorVersion: String by project
val logbackVersion: String by project
val jacksonVersion: String by project
val kotlinVersion: String by project

plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation(kotlin("test-junit"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("io.ktor:ktor-jackson:$ktorVersion")


    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-mapping"))
    implementation(project(":ok-workout-be-stubs"))
    implementation(project(":ok-workout-be-transport-openapi"))
}