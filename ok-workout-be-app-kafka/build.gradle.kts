plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("ru.otus.otuskotlin.workout.app.kafka.MainKt")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("(c) Otus")
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

dependencies {

    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val kotlinStdLibVersion: String by project
    val kotlinTestJunitVersion: String by project
    val slf4jVersion: String by project

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinStdLibVersion")

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-transport-openapi"))
    implementation(project(":ok-workout-be-mapping"))

    implementation(project(":ok-workout-be-service-openapi"))

    implementation(project(":ok-workout-be-logics"))

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinTestJunitVersion")

    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
}