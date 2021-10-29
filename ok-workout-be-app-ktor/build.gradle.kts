val ktorVersion: String by project
val logbackVersion: String by project
val jacksonVersion: String by project
val kotlinVersion: String by project

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass)
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")


    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation(kotlin("test-junit"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("io.ktor:ktor-jackson:$ktorVersion")

    // transport
    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-mapping"))
    implementation(project(":ok-workout-be-transport-openapi"))
    implementation(project(":ok-workout-be-logics"))

    implementation(project(":ok-workout-be-repo-inmemory"))


    implementation(project(":ok-workout-be-stubs"))

    // service
    implementation(project(":ok-workout-be-service-openapi"))
}