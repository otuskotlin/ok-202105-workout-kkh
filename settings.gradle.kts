rootProject.name = "workout"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openApiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion

        id("org.openapi.generator") version openApiVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion
    }
}

include("ok-m1l1")
include("m1l6")
include("common")
include("m2l2-testing")
include("ok-workout-be-transport-openapi")
include("ok-workout-be-common")
include("ok-workout-be-mapping")
include("ok-workout-be-app-ktor")
include("ok-workout-be-stubs")
include("ok-workout-be-service-openapi")
include("ok-workout-mp-common-cor")
include("ok-workout-be-logics")
include("ok-workout-mp-validation")
include("ok-workout-mp-cor-validation")
include("ok-workout-be-app-kafka")
