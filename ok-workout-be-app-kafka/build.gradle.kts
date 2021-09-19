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
//        ports.set(listOf(8080))
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

    implementation(kotlin("stdlib"))

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(project(":ok-workout-be-common"))
    implementation(project(":ok-workout-be-transport-openapi"))
    implementation(project(":ok-workout-be-mapping"))

    implementation(project(":ok-workout-be-service-openapi"))

    implementation(project(":ok-workout-be-logics"))

    testImplementation(kotlin("test-junit"))
}